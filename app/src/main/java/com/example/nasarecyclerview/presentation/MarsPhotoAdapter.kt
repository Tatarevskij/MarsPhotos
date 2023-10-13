package com.example.nasarecyclerview.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasarecyclerview.databinding.MarsPhotoItemBinding
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import com.example.nasarecyclerview.entity.MarsRoverPhotos
import javax.inject.Inject

class MarsPhotoAdapter @Inject constructor() : RecyclerView.Adapter<MarsPhotoViewHolder>() {
    private var marsRoverPhotosList: List<MarsRoverPhoto> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        val binding =
            MarsPhotoItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context),
                    parent,
                    false
                )
        return MarsPhotoViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhotoItem = marsRoverPhotosList.getOrNull(position)
        with(holder.binding) {
            cameraName.text = "Camera: ${marsPhotoItem?.camera?.cameraName}"
            roverName.text = "Rover: ${marsPhotoItem?.rover?.roverName}"
            sol.text = "Sol: ${marsPhotoItem?.sol}"
            date.text = "Date: ${marsPhotoItem?.date}"
            Glide.with(marsPhotoItemLayout.context)
                .load(marsPhotoItem?.imgSrc)
                .into(marsPhotoImageView)
        }
    }

    override fun getItemCount(): Int = marsRoverPhotosList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(marsRoverPhotos: MarsRoverPhotos) {
        marsRoverPhotosList = marsRoverPhotos.marsPhotosList
        notifyDataSetChanged()
    }
}