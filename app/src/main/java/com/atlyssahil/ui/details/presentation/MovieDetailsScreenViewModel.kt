package com.atlyssahil.ui.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlyssahil.common.network.repositories.MovieRepository
import com.atlyssahil.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            when (val response = movieRepository.getMovieDetails(movieId)) {
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
                            movieDetails = response.data,
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