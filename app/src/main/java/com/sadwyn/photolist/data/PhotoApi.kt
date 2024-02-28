package com.sadwyn.photolist.data

import com.sadwyn.photolist.data.model.PhotoList
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {
    @GET("/v1/curated")
    suspend fun getPhotoList(@Query("page") page: Int): PhotoList
}