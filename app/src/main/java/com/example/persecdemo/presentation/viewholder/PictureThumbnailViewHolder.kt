package com.example.persecdemo.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.persecdemo.databinding.LayoutItemPictureBinding
import com.example.persecdemo.usecase.model.ApodModel

class PictureThumbnailViewHolder(private val binding: LayoutItemPictureBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ApodModel, onItemClick: (ApodModel) -> Unit) {
        binding.headerTitleTv.text = model.title
        binding.descriptionTv.text = model.desc

        Glide.with(binding.root.context)
            .load(model.photoUrl)
            .centerCrop()
            .into(binding.imageView)

        binding.root.setOnClickListener { onItemClick(model) }
    }
}