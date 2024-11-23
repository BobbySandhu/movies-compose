package com.atlyssahil.ui

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object MovieListScreen : Screens

    @Serializable
    data class MovieDetailScreen(val id: Int) : Screens
}