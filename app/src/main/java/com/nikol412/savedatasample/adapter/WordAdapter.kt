package com.nikol412.savedatasample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikol412.savedatasample.data.response.WordItemUI
import com.nikol412.savedatasample.databinding.ListItemWordBinding

class WordAdapter : ListAdapter<WordItemUI, WordAdapter.WordViewHolder>(DiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            ListItemWordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class WordViewHolder(private val binding: ListItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val adapter by lazy { MeaningAdapter() }

        fun onBind(wordItem: WordItemUI) = with(binding) {
            rvWordMeanings.adapter = adapter
            tvWord.text = wordItem.word
            adapter.submitList(wordItem.meanings)
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<WordItemUI>() {
        override fun areItemsTheSame(oldItem: WordItemUI, newItem: WordItemUI): Boolean =
            oldItem.word == newItem.word

        override fun areContentsTheSame(oldItem: WordItemUI, newItem: WordItemUI): Boolean =
            oldItem == newItem
    }
}