package testes.yamdag1.API

/**
 * Created by Marco Rocha on 30/10/2017.
 */
import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class UsingVolleyApp : Application() {

    val requestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate() {
        super.onCreate()

    }

}

val Application.requestQueue : RequestQueue
    get() = (this as UsingVolleyApp).requestQueue
