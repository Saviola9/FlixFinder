package com.example.flixfinder.ui.movies.movie

import com.example.flixfinder.data.remote.MovieResponse
import com.example.flixfinder.util.Mapper
import com.example.flixfinder.util.toPosterUrl
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<MovieResponse, Movie> {
    override fun map(input: MovieResponse): Movie = Movie(
        id = input.id,
        name = input.name,
        releaseDate = input.firstAirDate.orEmpty(),
        posterPath = input.posterPath.orEmpty().toPosterUrl(),
        voteAverage = input.voteAverage,
        voteCount = input.voteCount
    )
}