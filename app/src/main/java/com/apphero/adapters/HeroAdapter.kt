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
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.net.Uri
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import java.io.File
import java.lang.Exception


class HeroAdapter(var heroes: List<Result>, val context: Context, val picasso: Picasso): RecyclerView.Adapter<HeroAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_hero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(heroes[position], context, picasso)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        fun bindItems(hero: Result, context: Context, picasso: Picasso) {
            val url = hero.thumbnail!!.path.plus("/portrait_small.").plus(hero.thumbnail.extension)
            //CustomPicasso.with(context).load(url).into(itemView.hero_image);
            picasso.isLoggingEnabled = true
            picasso.load(url).error(R.drawable.ic_launcher_foreground).into(itemView.hero_image, object : Callback{
                override fun onSuccess() {
                    Log.v("Picasso callback", "success")
                }

                override fun onError(e: Exception?) {
                    Log.v("Picasso error", e!!.message)
                }
            })
            //itemView.hero_image.setImageBitmap(getBitmapFromUri(Uri.parse(url)))
            itemView.hero_name.text = hero.name
            Log.v("Picasso", "Load Done")
        }
    }
}