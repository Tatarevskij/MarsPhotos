package com.example.nasarecyclerview.data

import com.example.nasarecyclerview.entity.MarsRoverPhotos
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject


private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/"

class MarsRoverPhotosRepository @Inject constructor(
) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val searchMarsRoverPhotoApi: SearchMarsRoverPhotoApi = retrofit.create(
        SearchMarsRoverPhotoApi::class.java
    )

    suspend fun getMarsRoverPhotos(solNumber: Int, page: Int): MarsRoverPhotos {
        return this.searchMarsRoverPhotoApi.getMarsRoverPhotosFromDto(solNumber, page)
    }
}

interface SearchMarsRoverPhotoApi {
    @GET("rovers/curiosity/photos?camera=navcam&api_key=$api_Key")
    suspend fun getMarsRoverPhotosFromDto(
        @Query("sol") solNumber: Int,
        @Query("page") page: Int
    ): MarsRoverPhotoDto

    private companion object {
        private const val api_Key = "ToKk5OnW1MDj8npCq6LO3TEhDvrAJCMKg40eoFEQ"
    }
}