package com.dicoding.mystory.adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mystory.data.ListStory
import com.dicoding.mystory.R

class ListStoryAdapter (private val listStory: ArrayList<ListStory>) : RecyclerView.Adapter<ListStoryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStory, viewHolder:ViewHolder)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_story, viewGroup, false))

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem.text = listStory[position].name
        Glide.with(viewHolder.itemView.context)
            .load(listStory[position].photoUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImg)
        viewHolder.itemView.setOnClickListener {

            onItemClickCallback.onItemClicked(listStory[viewHolder.adapterPosition],viewHolder)
        }

    }

    override fun getItemCount() = listStory.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tv_item_name)
        val itemImg: ImageView = view.findViewById(R.id.item_photo)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(story: List<ListStory>) {
        listStory.clear()
        listStory.addAll(story)
        notifyDataSetChanged()

    }

}