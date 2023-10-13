package com.example.nasarecyclerview.entity


interface MarsRoverPhoto {
    val id: String
    val imgSrc: String
    val rover: RoverParameters
    val camera: CameraParameters
    val sol: String
    val date: String
}

interface RoverParameters {
    val roverName: String
}

interface CameraParameters {
    val cameraName: String
}