package com.atlyssahil.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlyssahil.common.network.repositories.MovieRepository
import com.atlyssahil.common.utils.Resource
import com.atlyssahil.ui.home.data.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    /* trending movies cached data */
    private var trendingMovies = mutableListOf<Movie>()

    private var searchJob: Job? = null

    init {
        getTrendingMovies()
    }

    fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            // checking if cache is available
            if (trendingMovies.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trendingMovies = trendingMovies,
                        error = null,
                        searchQuery = ""
                    )
                }
            }

            when (val response = movieRepository.getTrendingMovies()) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message,
                            searchQuery = ""
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            trendingMovies = response.data?.movies ?: emptyList(),
                            error = null,
                            searchQuery = ""
                        )
                    }
                }

                else -> {
                    uiState
                }
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        _uiState.update {
            it.copy(searchQuery = searchTerm)
        }
    }

    fun search(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            if (query.isEmpty()) {
                onSearchClear()
                return@launch
            }

            when (val response = movieRepository.searchMovie(searchQuery = query)) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }

                is Resource.Success -> {
                    if (response.data?.searches?.isNotEmpty() == true) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                trendingMovies = response.data.searches,
                                error = null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                trendingMovies = emptyList(),
                                error = "No result found"
                            )
                        }
                    }
                }

                else -> {
                    uiState
                }
            }
        }
    }

    fun onSearchClear() {
        if (trendingMovies.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    trendingMovies = trendingMovies,
                    error = null,
                    searchQuery = ""
                )
            }
        } else {
            getTrendingMovies()
        }
    }
}