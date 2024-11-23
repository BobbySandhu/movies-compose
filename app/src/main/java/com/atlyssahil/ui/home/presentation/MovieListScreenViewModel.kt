package com.atlyssahil.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlyssahil.common.network.repositories.MovieRepository
import com.atlyssahil.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTrendingMovies()
    }

    fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            when (val response = movieRepository.getTrendingMovies()) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            trendingMovies = response.data?.movies ?: emptyList(),
                            error = null
                        )
                    }
                }

                else -> {
                    uiState
                }
            }
        }
    }
}