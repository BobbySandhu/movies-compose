package com.atlyssahil.ui.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atlyssahil.R
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

    LaunchedEffect(Unit) {
        viewModel.getTrendingMovies()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(searchQuery = searchQuery)
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(uiState.isLoading) {
                CircularProgressIndicator()
            }


            MovieList(uiState = uiState, onItemClick = onItemClick)

            ErrorAndRetryUi(uiState, viewModel)
        }
    }
}

@Composable
private fun ErrorAndRetryUi(
    uiState: MovieListUiState,
    viewModel: MovieListScreenViewModel
) {
    AnimatedVisibility(!uiState.isLoading && !uiState.error.isNullOrEmpty() && uiState.trendingMovies.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.error ?: stringResource(R.string.some_error_occurred),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    viewModel.getTrendingMovies()
                }
            ) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun MovieList(uiState: MovieListUiState, onItemClick: (Screens.MovieDetailScreen) -> Unit) {
    //AnimatedVisibility(!uiState.isLoading && uiState.trendingMovies.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                uiState.trendingMovies,
                key = {
                    it.id
                }
            ) { movie ->
                CardViewMovie(
                    imageUrl = Constants.IMAGE_BASE_URL + movie.backdropPath,
                    id = movie.id,
                    title = movie.title,
                    onItemClick = onItemClick
                )
            }
        }
    //}
}