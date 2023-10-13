package com.example.nasarecyclerview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.nasarecyclerview.data.MarsPhotoPagingSource
import com.example.nasarecyclerview.domain.GetMarsPhotoUseCase
import com.example.nasarecyclerview.entity.EmptyMarsPhotoList
import com.example.nasarecyclerview.entity.MarsRoverPhoto
import com.example.nasarecyclerview.entity.MarsRoverPhotos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMarsPhotoUseCase: GetMarsPhotoUseCase,

    ) : ViewModel() {
    var sol: Int = 0
    private val _marsRoverPhotosFlow = MutableStateFlow<MarsRoverPhotos>(EmptyMarsPhotoList())
    val marsRoverPhotosFlow = _marsRoverPhotosFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private fun loadMarsPhotosPagingFlow(): Flow<PagingData<MarsRoverPhoto>> {
       return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = { MarsPhotoPagingSource(sol) }
        ).flow.cachedIn(viewModelScope)
    }

    fun loadMarsPhotos(marsPhotosPagedAdapter: MarsPhotosPagedAdapter) {
        viewModelScope.launch {
            marsPhotosPagedAdapter.loadStateFlow.collect {
                _isLoading.value = it.source.refresh is LoadState.Loading
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            loadMarsPhotosPagingFlow().collect {
                marsPhotosPagedAdapter.submitData(it)
            }
        }
    }

    /* init {
         loadMarsPhotos()
     }
     private fun loadMarsPhotos() {
         viewModelScope.launch(Dispatchers.IO) {
             _isLoading.value = true
             kotlin.runCatching {
                 getMarsPhotoUseCase.execute(1, 1)
             }.fold(
                 onSuccess = { _marsRoverPhotosFlow.value = it },
                 onFailure = { Log.d("MainViewModel", it.message ?: "") }
             )
             delay(500)
             _isLoading.value = false
         }
     }*/
}