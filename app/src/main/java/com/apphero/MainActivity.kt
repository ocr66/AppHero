package com.apphero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.apphero.adapters.HeroAdapter
import com.apphero.util.toMD5
import com.apphero.dataclass.ApiResponse
import com.apphero.dataclass.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.apphero.callbacks.CustomCallback
import com.apphero.util.MarvelApiService


class MainActivity : AppCompatActivity(), CustomCallback {

    lateinit var heroes: ApiResponse
    lateinit var picasso: Picasso
    var lastOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heroes_list.layoutManager = LinearLayoutManager(this)
        picasso = Picasso.get()

        heroes_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisiblePosition = (heroes_list.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisiblePosition == heroes.data!!.results!!.size-1 && lastVisiblePosition != lastOffset) {
                    lastOffset = lastVisiblePosition
                    progressBar.visibility = View.VISIBLE
                    getMarvelDataOffset(heroes.data!!.results!!.size)
                }
            }
        })


        val intent = intent
        heroes = intent.getSerializableExtra("heroes") as ApiResponse
        if(heroes != null){
            if(heroes.status == ("Ok")){
                heroes_list.adapter = HeroAdapter(heroes.data!!.results!!, picasso, this)
            }
        } else {
            val ts = getTimestamp()
            val hashKey = getEncodedKeys(ts)
            getRetrofit()
            getMarvelData(ts, hashKey)
        }

    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(
            resources.getString(R.string.marvel_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getMarvelData(ts: String, hashKey: String) {
        doAsync {
            val call = getRetrofit()
                .create(MarvelApiService::class.java)
                .getMarvelHeroes(ts, resources.getString(R.string.public_key), hashKey).execute()
                val heroes = call.body() as ApiResponse
            uiThread {
                if(heroes.status == ("Ok")){
                    heroes_list.adapter = HeroAdapter(heroes.data!!.results!!, picasso, this@MainActivity)
                }
            }
        }
    }

    fun getMarvelDataOffset(offset: Int) {
        val ts = getTimestamp()
        val hashKey = getEncodedKeys(ts)
        doAsync {
            val call = getRetrofit()
                .create(MarvelApiService::class.java)
                .getMarvelHeroesOffset(ts, resources.getString(R.string.public_key), hashKey, offset).execute()
            val heroes = call.body() as ApiResponse
            uiThread {
                if(heroes.status == ("Ok")){
                    updateAdapterOffset(heroes)
                }
            }
        }
    }

    private fun getEncodedKeys(ts: String): String{
        val stringToMD5 =
            ts.plus(resources.getString(R.string.private_key))
                .plus(resources.getString(R.string.public_key))
        return stringToMD5.toMD5()
    }

    fun getTimestamp(): String{
        return (System.currentTimeMillis()/1000).toString()
    }

    private fun updateAdapterOffset(newHeroes: ApiResponse){
        heroes.data!!.results!!.addAll(newHeroes.data!!.results!!)
        progressBar.visibility = View.GONE
        updateAdapter()
    }

    override fun updateAdapter() {
        (heroes_list.adapter as HeroAdapter).notifyDataSetChanged()
    }

    override fun getMoreHeroes(offset: Int) {
        getMarvelDataOffset(offset)
        updateAdapter()
    }

    override fun viewHeroDetails(hero: Result) {
        val intent = Intent(this, HeroDetails::class.java)
        intent.putExtra("hero", hero)
        startActivity(intent)
    }
}
