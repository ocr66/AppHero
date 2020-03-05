package com.apphero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apphero.adapters.ComicAdapter
import com.apphero.dataclass.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.hero_details.*

class HeroDetails: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hero_details)

        val intent = intent
        val hero: Result = intent.getSerializableExtra("hero") as Result

        Picasso.get().load(hero.thumbnail!!.path.plus("/portrait_uncanny.").plus(hero.thumbnail.extension)).into(hero_details_image)
        hero_details_name.text = hero.name
        comics_list_view.layoutManager = LinearLayoutManager(this)
        comics_list_view.adapter = ComicAdapter(hero.comics!!.items!!)
    }
}