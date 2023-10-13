package com.example.nasarecyclerview.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.nasarecyclerview.databinding.MarsPhotoItemBinding
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import javax.inject.Inject

class MarsPhotosPagedAdapter @Inject constructor(
    private val onClick: (MarsRoverPhoto) -> Unit
) :
    PagingDataAdapter<MarsRoverPhoto, MarsPhotoViewHolder>(DifutiilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding = MarsPhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarsPhotoViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhotoItem = getItem(position)
        with(holder.binding) {
            cameraName.text = "Camera: ${marsPhotoItem?.camera?.cameraName}"
            roverName.text = "Rover: ${marsPhotoItem?.rover?.roverName}"
            sol.text = "Sol: ${marsPhotoItem?.sol}"
            date.text = "Date: ${marsPhotoItem?.date}"
            Glide.with(marsPhotoItemLayout.context)
                .load(marsPhotoItem?.imgSrc)
                .into(marsPhotoImageView)
        }
        holder.binding.marsPhotoItemLayout.setOnClickListener {
            marsPhotoItem?.let {
                onClick(marsPhotoItem)
            }
        }
    }
}

class DifutiilCallback : DiffUtil.ItemCallback<MarsRoverPhoto>() {
    override fun areItemsTheSame(oldItem: MarsRoverPhoto, newItem: MarsRoverPhoto): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsRoverPhoto, newItem: MarsRoverPhoto): Boolean =
        oldItem.imgSrc == newItem.imgSrc
}