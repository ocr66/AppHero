package com.apphero.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apphero.R
import com.apphero.dataclass.Result
import kotlinx.android.synthetic.main.item_custom_hero.view.*
import com.apphero.callbacks.CustomCallback
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class HeroAdapter(var heroes: List<Result>, val picasso: Picasso, val callback: CustomCallback): RecyclerView.Adapter<HeroAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_hero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(heroes[position], picasso, callback)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        fun bindItems(hero: Result, picasso: Picasso, callback: CustomCallback) {
            val url = hero.thumbnail!!.path.plus("/portrait_small.").plus(hero.thumbnail.extension)
            picasso.isLoggingEnabled = false
            picasso.load(url).error(R.drawable.ic_launcher_foreground).into(itemView.hero_image, object : Callback{
                override fun onSuccess() {
                    Log.d("Picasso callback", "success")
                }

                override fun onError(e: Exception?) {
                    Log.d("Picasso error", e!!.message)
                }
            })
            itemView.hero_name.text = hero.name

            itemView.hero_item_layout.setOnClickListener{
                callback.viewHeroDetails(hero)
            }

            Log.v("Picasso", "Load Done")
        }
    }
}