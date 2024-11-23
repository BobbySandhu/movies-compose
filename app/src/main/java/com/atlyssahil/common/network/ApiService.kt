package com.atlyssahil.common.network

import com.atlyssahil.BuildConfig
import com.atlyssahil.BuildConfig.API_KEY
import com.atlyssahil.ui.details.data.models.MovieDetails
import com.atlyssahil.ui.home.data.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieDetails
}