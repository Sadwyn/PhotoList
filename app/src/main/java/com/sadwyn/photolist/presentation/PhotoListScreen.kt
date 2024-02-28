package com.sadwyn.photolist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import com.sadwyn.photolist.data.model.Photo
import com.sadwyn.photolist.presentation.navigation.Destination
import com.sadwyn.photolist.presentation.navigation.withParam
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    navController: NavController, viewModel: PhotoListViewModel
) {
    val photos = viewModel.photoFlow.collectAsLazyPagingItems()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val isRefreshing by remember {
            mutableStateOf(false)
        }
        val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
            photos.refresh()
        })
        when (val state = photos.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
        when (val state = photos.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }

        when (val state = photos.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }

        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier
                .fillMaxSize()
                .pullRefresh(state)
        ) {

            items(count = photos.itemCount)
            { index ->
                val photo = photos[index]
                PhotoItem(photo!!, navController)
            }


        }
        PullRefreshIndicator(
            refreshing = isRefreshing, state = state,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun PhotoItem(photo: Photo, navController: NavController) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                val encodedUrl =
                    URLEncoder.encode(
                        photo.src?.large2x,
                        StandardCharsets.UTF_8.toString()
                    )
                navController.navigate(Destination.PhotoDetails.withParam(encodedUrl))
            },
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            SubcomposeAsyncImage(
                model = photo.src?.medium, contentDescription = photo.alt,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 250.dp),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
            )
        }
        Text(
            text = photo.photographer!!,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(16.dp)
            .width(150.dp)
            .height(150.dp)
            .zIndex(100f)
    )
}

@Composable
private fun Error(
    message: String
) {
    Text(
        text = message,
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.error
    )
}