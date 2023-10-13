package com.example.nasarecyclerview.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.nasarecyclerview.databinding.MarsPhotoItemBinding
import javax.inject.Inject

class MarsPhotoViewHolder @Inject constructor
    (val binding: MarsPhotoItemBinding):
        RecyclerView.ViewHolder(binding.root)
