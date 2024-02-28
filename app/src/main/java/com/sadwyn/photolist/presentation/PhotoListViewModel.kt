package com.sadwyn.photolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sadwyn.photolist.data.PhotoPagingSource
import com.sadwyn.photolist.data.model.Photo
import com.sadwyn.photolist.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private var photoRepository: PhotoRepository
) : ViewModel() {


    val photoFlow: Flow<PagingData<Photo>> = Pager(
        pagingSourceFactory = { PhotoPagingSource(photoRepository) },
        config = PagingConfig(pageSize = 15, prefetchDistance = 3)
    ).flow.cachedIn(viewModelScope)

}