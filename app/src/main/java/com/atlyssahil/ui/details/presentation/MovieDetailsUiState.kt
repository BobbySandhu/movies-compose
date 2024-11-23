package com.atlyssahil.ui.details.presentation

import com.atlyssahil.ui.details.data.models.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)