package com.sadwyn.photolist.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sadwyn.photolist.data.model.Photo
import com.sadwyn.photolist.data.repository.PhotoRepository
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(private val photoRepository: PhotoRepository) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> =
        try {
            val page = params.key ?: 0
            val data = photoRepository.loadPhotos(page)
            LoadResult.Page(
                data = data.photos,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.photos.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}