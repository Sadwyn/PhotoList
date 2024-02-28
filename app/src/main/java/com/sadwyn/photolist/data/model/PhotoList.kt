package com.sadwyn.photolist.data.model

import com.google.gson.annotations.SerializedName


data class PhotoList(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("per_page") var perPage: Int? = null,
    @SerializedName("photos") var photos: ArrayList<Photo> = arrayListOf(),
    @SerializedName("next_page") var nextPage: String? = null
)