package com.atlyssahil.ui.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atlyssahil.common.presentation.components.CardViewMovie
import com.atlyssahil.common.presentation.components.SearchBar
import com.atlyssahil.common.utils.Constants
import com.atlyssahil.ui.Screens

@Composable
fun MovieListScreen(
    viewModel: MovieListScreenViewModel = hiltViewModel(),
    onItemClick: (Screens.MovieDetailScreen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar()
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            androidx.compose.animation.AnimatedVisibility(uiState.isLoading) {
                CircularProgressIndicator()
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(uiState.trendingMovies) { movie ->
                    CardViewMovie(
                        imageUrl = Constants.IMAGE_BASE_URL + movie.backdropPath,
                        id = movie.id,
                        title = movie.title,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}