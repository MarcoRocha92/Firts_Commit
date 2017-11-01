package testes.yamdag1

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import testes.yamdag1.API.requestQueue
import testes.yamdag1.Model.MovieDetails
import java.util.*

/**
 * Created by DiogoM on 31/10/2017.
 */

 class MovieDetailsActivity : AppCompatActivity(){

    private val language : String = "&language=" + Locale.getDefault().toString()
    private val apiKey = "?api_key=a175de71cf743a742d8c0b9b83f80322"
    private val url = "https://api.themoviedb.org/3/movie/"
    var txtvw_title : TextView? = null
    var txtvw_origtitle : TextView? = null
    var txtvw_date : TextView? = null
    var txtvw_vote : TextView? = null
    var txtvw_overview : TextView? = null
    var imgvw_image : ImageView? = null
    val color : testes.yamdag1.View.Color? = null



     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(testes.yamdag1.R.layout.activity_moviedetails)

         val movieId:String = intent.getStringExtra("movieid")
         val queryUrl = url+movieId+apiKey+language

         txtvw_title = findViewById(R.id.title) as TextView
         txtvw_origtitle = findViewById(R.id.orig_title) as TextView
         txtvw_date = findViewById(R.id.date) as TextView
         txtvw_vote = findViewById(R.id.vote) as TextView
         txtvw_overview = findViewById(R.id.overview) as TextView
         imgvw_image = findViewById(R.id.image) as ImageView

         application.requestQueue.add(JsonObjectRequest(
                 queryUrl, null,
                 {
                  val details =   MovieDetails(
                             it["title"] as String,
                             it["original_title"] as String,
                             it["release_date"] as String,
                             it["vote_average"] as Number,
                             it["poster_path"] as? String,
                             it["overview"] as String

                     )
                     txtvw_title?.setTextColor(Color.WHITE)
                     txtvw_origtitle?.setTextColor(Color.WHITE)
                     txtvw_date?.setTextColor(Color.WHITE)
                     txtvw_vote?.setTextColor(Color.WHITE)
                     txtvw_overview?.setTextColor(Color.WHITE)
                     txtvw_title?.setText(getResources().getString(R.string.title)+ " " +details.title).toString()
                     txtvw_origtitle?.setText(getResources().getString(R.string.orig_title)+ " " +details.orig_title).toString()
                     txtvw_date?.setText(getResources().getString(R.string.date)+ " " +details.date).toString()
                     txtvw_vote?.setText(getResources().getString(R.string.vote)+ " " +details.vote.toString()).toString()
                     txtvw_overview?.setText(getResources().getString(R.string.overview)+ " " +details.overview).toString()

                     val url = "https://image.tmdb.org/t/p/w500/" + details.image
                     Picasso.with(this).load(url).into(imgvw_image)

                 },
                 {
                     Toast.makeText(this, "::ERROR::", Toast.LENGTH_SHORT).show()
                 }

         ))
         //Toast.makeText(this,"fim",Toast.LENGTH_SHORT).show()
     }
 }




