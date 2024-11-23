package com.atlyssahil.common.network.repositories

import com.atlyssahil.common.network.ApiService
import com.atlyssahil.common.utils.Resource
import com.atlyssahil.ui.details.data.models.MovieDetails
import com.atlyssahil.ui.home.data.models.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getTrendingMovies(): Resource<MovieResponse> {
        val response = try {
            apiService.getTrendingMovies()
        } catch (e: Exception) {
            return Resource.Error("error occurred: ${e.message}")
        }

        return Resource.Success(response)
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        val response = try {
            apiService.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("error occurred: ${e.message}")
        }

        return Resource.Success(response)
    }
}