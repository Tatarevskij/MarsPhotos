package com.example.nasarecyclerview.data

import com.example.nasarecyclerview.entity.CameraParameters
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import com.example.nasarecyclerview.entity.MarsRoverPhotos
import com.example.nasarecyclerview.entity.RoverParameters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class MarsRoverPhotoDto @Inject constructor(
    @Json(name = "photos") override val marsPhotosList: List<Photo>
) : MarsRoverPhotos {
    @JsonClass(generateAdapter = true)
    data class Photo(
        @Json(name = "id") override  val id: String,
        @Json(name = "img_src") override val imgSrc: String,
        @Json(name = "sol") override val sol: String,
        @Json(name = "earth_date") override val date: String,
        @Json(name = "rover") override val rover: Rover,
        @Json(name = "camera") override val camera: Camera,
    ) : MarsRoverPhoto {
        @JsonClass(generateAdapter = true)
        data class Rover(
            @Json(name = "name") override val roverName: String,
        ) : RoverParameters

        @JsonClass(generateAdapter = true)
        data class Camera(
            @Json(name = "name") override val cameraName: String,
        ) : CameraParameters
    }
}