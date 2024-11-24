package com.atlyssahil.ui.home.presentation

import com.atlyssahil.ui.home.data.models.Movie

data class MovieListUiState(
    val isLoading: Boolean = true,
    val trendingMovies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)