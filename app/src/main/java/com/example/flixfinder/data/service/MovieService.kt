package com.example.flixfinder.data.service

import com.example.flixfinder.data.remote.CreditsResponse
import com.example.flixfinder.data.remote.GenresResponse
import com.example.flixfinder.data.remote.ImagesResponse
import com.example.flixfinder.data.remote.MovieDetailResponse
import com.example.flixfinder.data.remote.MoviesResponse

interface MovieService {

    suspend fun fetchMovies(pageNumber : Int, options : Map<String, String>) : MoviesResponse

    suspend fun search(pageNumber: Int, searchQuery : String, includeAdult : Boolean = true) : MoviesResponse

    suspend fun fetchGenres() : GenresResponse

    suspend fun fetchMovieDetail(movieId : Int) : MovieDetailResponse

    suspend fun fetchMovieCredits(movieId: Int) : CreditsResponse

    suspend fun fetchMovieImages(movieId : Int) : ImagesResponse
}