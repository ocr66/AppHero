package com.apphero.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apphero.R
import com.apphero.dataclass.ComicItem
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicAdapter(var comics: List<ComicItem>): RecyclerView.Adapter<ComicAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(comics[position], position)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItems(comics: ComicItem, position: Int){
            itemView.comic_name.text = (position +1).toString().plus(". ").plus(comics.name)
        }
    }
}