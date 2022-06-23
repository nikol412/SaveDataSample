package com.nikol412.savedatasample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikol412.savedatasample.databinding.ListItemWordMeaningBinding

class MeaningAdapter : ListAdapter<String, MeaningAdapter.MeaningViewHolder>(DiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder =
        MeaningViewHolder(
            ListItemWordMeaningBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class MeaningViewHolder(private val binding: ListItemWordMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: String) {
            binding.tvWordMeaning.text = item
        }
    }

    private class DiffItemCallback() : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
}

