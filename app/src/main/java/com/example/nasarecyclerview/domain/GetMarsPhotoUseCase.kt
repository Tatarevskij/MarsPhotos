package com.example.nasarecyclerview.domain

import com.example.nasarecyclerview.data.MarsRoverPhotosRepository
import com.example.nasarecyclerview.entity.MarsRoverPhotos
import javax.inject.Inject

class GetMarsPhotoUseCase @Inject constructor(
    private val marsRoverPhotosRepository: MarsRoverPhotosRepository
) {
    suspend fun execute(solNumber: Int, page: Int): MarsRoverPhotos {
        return marsRoverPhotosRepository.getMarsRoverPhotos(solNumber,page)
    }
}