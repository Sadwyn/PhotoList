package com.sadwyn.photolist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sadwyn.photolist.presentation.PhotoDetailsScreen
import com.sadwyn.photolist.presentation.PhotoListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.PhotoList.route) {
        composable(route = Destination.PhotoList.route) {
            PhotoListScreen(navController, hiltViewModel())
        }
        composable(route = Destination.PhotoDetails.route) {
            val largePhotoUrl = it.arguments?.getString("largePhotoUrl")
            PhotoDetailsScreen(navController, largePhotoUrl)
        }
    }
}