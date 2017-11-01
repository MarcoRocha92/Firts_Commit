package testes.yamdag1


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import testes.yamdag1.API.requestQueue
import testes.yamdag1.Model.Movie
import java.util.*

class MainActivity : AppCompatActivity() {

    class MvViewHolder(val titleTextView: TextView, val dateTextView: TextView/*, val voteTextView: TextView*/) {

    }
    private val language : String = "&language=" + Locale.getDefault().toString()
    private val apiKey = "?api_key=a175de71cf743a742d8c0b9b83f80322"
    private val url = "https://api.themoviedb.org/3/"
    var editText : EditText? = null
    var listview : ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText) as EditText
        listview = findViewById(R.id.ListView) as ListView
        // https://api.themoviedb.org/3/movie/550?api_key=a175de71cf743a742d8c0b9b83f80322

    }


    fun onSearch(view: View?) {
        val search_URI = url + "search/movie" + apiKey + "&query=" + editText?.getText().toString()+language
        handler(search_URI)
    }


    public fun onTheaters(view: View?) {
        //Toast.makeText(this, "em cartaz", Toast.LENGTH_SHORT).show()
        val theaters_URI = url + "movie/now_playing" + apiKey+language
        handler(theaters_URI)
    }

    public fun onCommingSoon(view: View?) {
        //Toast.makeText(this, "Brevemente", Toast.LENGTH_SHORT).show()
        val theaters_URI = url + "movie/upcoming" + apiKey+language
        handler(theaters_URI)
    }

    public fun onPopulars(view: View?) {
        //Toast.makeText(this, "Populares", Toast.LENGTH_SHORT).show()
        val theaters_URI = url + "movie/popular" + apiKey+language
        handler(theaters_URI)
    }




    fun handler(url_URI:String){
        application.requestQueue.add(JsonObjectRequest(
                url_URI.replace(' ', '+'), null,
                {
                    val jsonMovies = it.get("results") as JSONArray

                    val movies = jsonMovies
                            .asSequence()
                            .map {
                                Movie(
                                        it["id"] as Int,
                                        it["title"] as String,
                                        it["release_date"] as String,
                                        it["vote_average"] as Number
                                )
                            }
                            .toList()
                            .toTypedArray()

                    val itemLayoutId = android.R.layout.simple_list_item_2
                    val inflater = LayoutInflater.from(this)
                    val adapter = object : ArrayAdapter<Movie>(this, itemLayoutId, movies) {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                            val itemView =
                                    convertView ?:
                                            inflater.inflate(itemLayoutId, parent, false)
                            if (convertView == null) {
                                //val image = itemView.findViewById<ImageView>(android.R.id.textAssist)
                                val text1 = itemView.findViewById<TextView>(android.R.id.text1)
                                val text2 = itemView.findViewById<TextView>(android.R.id.text2)

                                itemView.tag = MvViewHolder(text1, text2)
                            }

                            val mvViewHolder = itemView.tag as MvViewHolder
                            //Picasso.with(applicationContext).load(url+ "/images"+apiKey+"&").into(image)
                            mvViewHolder.titleTextView.setText(movies[position].title)
                            mvViewHolder.dateTextView.setText(movies[position].release_date)
                            //mvViewHolder.voteTextView.setText(movies[position].vote.toInt())

                            return itemView
                        }
                    }

                    listview?.adapter = adapter

                    listview?.setOnItemClickListener { parent, view, position, id ->

                        val intent = Intent(applicationContext,MovieDetailsActivity::class.java);
                        intent.putExtra("movieid",movies[position].id.toString())
                        startActivity(intent);
                    }


                    //Picasso.with(applicationContext).load(endpoints.searchImage("requrl")).into("vossa image view")


                },
                {
                    Toast.makeText(this, "::ERROR::", Toast.LENGTH_SHORT).show()
                }

        ))
    }

    fun JSONArray.asSequence() =
            (0 until length()).asSequence().map { get(it) as JSONObject }
}
