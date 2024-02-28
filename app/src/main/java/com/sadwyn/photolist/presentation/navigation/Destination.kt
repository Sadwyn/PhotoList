package com.sadwyn.photolist.presentation.navigation

sealed class Destination(open val route: String) {
    data object PhotoList : Destination("PhotoList")
    data object PhotoDetails : Destination("PhotoDetails/{largePhotoUrl}")
}

fun Destination.withParam(param: String): String {
    return route.substringBefore("/").plus("/").plus(param)
}