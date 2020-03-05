package com.apphero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.apphero.adapters.HeroAdapter
import com.apphero.adapters.toMD5
import com.apphero.dataclass.ApiResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity(), Callback {


    lateinit var heroesList: HeroAdapter
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var ts = getTimestamp()
        var hashKey = getEncodedKeys(ts)
        getRetrofit()
        getMarvelData(ts, hashKey)
        picasso = Picasso.get()

        heroes_list.layoutManager = LinearLayoutManager(this)



    }

    fun getRetrofit(): Retrofit {
        val retrofitBuilder = Retrofit.Builder().baseUrl(
            resources.getString(R.string.marvel_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder
    }

    fun getMarvelData(ts: String, hashKey: String) {
        doAsync {
            val call = getRetrofit()
                .create(MarvelApiService::class.java)
                .getMarvelHeroes(ts, resources.getString(R.string.public_key), hashKey).execute()
                val heroes = call.body() as ApiResponse
                Log.v("Heroes Response", call.message())
                Log.v("Heroes Response", heroes.status)
            uiThread {
                if(heroes.status == ("Ok")){
                    heroes_list.adapter = HeroAdapter(heroes.data!!.results!!, this@MainActivity, picasso)
                }
            }
        }
    }

    fun getEncodedKeys(ts: String): String{
        val stringToMD5 =
            ts.plus(resources.getString(R.string.private_key))
                .plus(resources.getString(R.string.public_key))
        return stringToMD5.toMD5()
    }

    fun getTimestamp(): String{
        return (System.currentTimeMillis()/1000).toString()
    }

    override fun updateAdapter() {
        (heroes_list.adapter as HeroAdapter).notifyDataSetChanged()
    }
}
