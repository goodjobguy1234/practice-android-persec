package com.example.persecdemo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.persecdemo.databinding.LayoutItemPictureBinding
import com.example.persecdemo.presentation.viewholder.PictureThumbnailViewHolder
import com.example.persecdemo.usecase.model.ApodModel

class PictureOfDaysListAdapter(private val onItemClick: (ApodModel) -> Unit) :
    ListAdapter<ApodModel, PictureThumbnailViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureThumbnailViewHolder {
        val binding = LayoutItemPictureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PictureThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureThumbnailViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, onItemClick)
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<ApodModel>() {
        override fun areItemsTheSame(oldItem: ApodModel, newItem: ApodModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ApodModel, newItem: ApodModel): Boolean {
            return oldItem == newItem
        }
    }
}