package com.apphero

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.apphero.util.toMD5
import com.apphero.dataclass.ApiResponse
import com.apphero.util.MarvelApiService
import org.jetbrains.anko.doAsync
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)

        val ts = getTimestamp()
        val hashKey = getEncodedKeys(ts)
        getRetrofit()
        getMarvelData(ts, hashKey)
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(
            resources.getString(R.string.marvel_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getMarvelData(ts: String, hashKey: String) {
        var heroes: ApiResponse
        doAsync {
            val call = getRetrofit()
                .create(MarvelApiService::class.java)
                .getMarvelHeroes(ts, resources.getString(R.string.public_key), hashKey).execute()
            heroes = call.body() as ApiResponse
            runOnUiThread {
                if(heroes.status == ("Ok")){
                    returnHeroes(heroes)
                }
            }
            Log.v("Heroes Response", call.message())
            Log.v("Heroes Response", heroes.status)
        }
    }

    private fun returnHeroes(heroes: ApiResponse){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("heroes", heroes)
        startActivity(intent)
        finish()

    }

    private fun getEncodedKeys(ts: String): String{
        val stringToMD5 =
            ts.plus(resources.getString(R.string.private_key))
                .plus(resources.getString(R.string.public_key))
        return stringToMD5.toMD5()
    }

    private fun getTimestamp(): String{
        return (System.currentTimeMillis()/1000).toString()
    }
}