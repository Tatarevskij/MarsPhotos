package com.example.nasarecyclerview.entity

data class EmptyMarsPhotoList(
    override val marsPhotosList: List<MarsRoverPhoto> = emptyList()
) : MarsRoverPhotos