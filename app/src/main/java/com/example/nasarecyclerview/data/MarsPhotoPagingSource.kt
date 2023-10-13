package com.example.nasarecyclerview.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import javax.inject.Inject

class MarsPhotoPagingSource @Inject constructor(
    private val solNumber: Int
) : PagingSource<Int, MarsRoverPhoto>()  {
    private val marsPhotosRepository = MarsRoverPhotosRepository()
    override fun getRefreshKey(state: PagingState<Int, MarsRoverPhoto>): Int = FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarsRoverPhoto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            marsPhotosRepository.getMarsRoverPhotos(solNumber, page).marsPhotosList
        }.fold(
            onSuccess = {
                println(it)
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it) }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}