package com.sadwyn.photolist.data.repository

import com.sadwyn.photolist.data.PhotoApi
import com.sadwyn.photolist.data.model.PhotoList
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val api: PhotoApi) {

    suspend fun loadPhotos(page: Int) : PhotoList {
        return api.getPhotoList(page)
    }
}