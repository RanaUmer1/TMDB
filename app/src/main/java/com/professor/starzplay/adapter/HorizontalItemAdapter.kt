package com.professor.starzplay.adapter

import AppUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.professor.data_source.model.MediaItem
import com.professor.starzplay.R
import com.professor.starzplay.databinding.ItemHorizontalBinding

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */


class HorizontalItemAdapter(
    private val items: List<MediaItem>,
    private val onClick: (MediaItem) -> Unit
) : RecyclerView.Adapter<HorizontalItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaItem) {
            val imagePath = when (item.media_type) {
                "person" -> {
                    Log.e("TAG", "person: ${item.profile_path}")
                    item.profile_path

                }

                else -> item.poster_path ?: item.backdrop_path
            }
            val imageUrl = imagePath?.let { "https://image.tmdb.org/t/p/w500$it" }

            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.place_holder_bg) // Optional
                .error(R.drawable.place_holder_bg)
                .into(binding.imageView)

            binding.voteCount.text = item.vote_count?.let { AppUtils.formatVoteCount(it) }
            binding.title.text = item.title ?: item.name ?: "No Title"
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
