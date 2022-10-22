package com.dicoding.mystory.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mystory.data.fordb.StoryResponseDB
import com.dicoding.mystory.databinding.ListStoryBinding
import com.dicoding.mystory.view.detailview.DetailViewActivity

class StoryListAdapter :
    PagingDataAdapter<StoryResponseDB, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK)  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

    }

    class MyViewHolder(private val binding: ListStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: StoryResponseDB?) {
            with(binding) {
                result?.apply {
                    tvItemName.text = name
                    Glide.with(binding.root)
                        .load(photoUrl)
                        .into(itemPhoto)

                    itemView.setOnClickListener {
                        val optionsCompat: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                Pair(itemPhoto,  "photo"),
                                Pair(tvItemName, "name")
                            )
                        Intent(itemView.context, DetailViewActivity::class.java)
                            .apply {
                                putExtra(EXTRA_STORY, result)
                                itemView.context.startActivity(
                                    this,
                                    optionsCompat.toBundle()
                                )
                            }
                    }
                }
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseDB>() {
            override fun areItemsTheSame(oldItem: StoryResponseDB, newItem: StoryResponseDB): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryResponseDB, newItem: StoryResponseDB): Boolean {
                return oldItem.id == newItem.id
            }

        }
        const val EXTRA_STORY = "extra_story"
    }
}