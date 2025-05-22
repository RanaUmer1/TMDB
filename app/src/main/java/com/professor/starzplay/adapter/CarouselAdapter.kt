package com.professor.starzplay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professor.data_source.model.MediaItem
import com.professor.starzplay.databinding.ItemCarouselBinding

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */


class CarouselAdapter(
    private val groupedItems: Map<String, List<MediaItem>>,
    private val onItemClick: (MediaItem) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private val keys = groupedItems.keys.sorted()

    inner class CarouselViewHolder(val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun getItemCount(): Int = keys.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val type = keys[position]
        val items = groupedItems[type] ?: emptyList()

        holder.binding.carouselTitle.text = type.uppercase()
        holder.binding.horizontalRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalItemAdapter(items, onItemClick)
        }
    }
}