package com.example.flixfinder.ui.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flixfinder.data.service.MovieService
import com.example.flixfinder.ui.filter.FilterState
import com.example.flixfinder.ui.filter.MovieRequestOptionsMapper
import com.example.flixfinder.ui.movies.movie.Movie
import com.example.flixfinder.ui.movies.movie.MovieMapper

class MoviesPagingSource(
    private val movieService: MovieService,
    private val movieMapper: MovieMapper,
    movieRequestOptionsMapper: MovieRequestOptionsMapper,
    filterState: FilterState? = null,
    private val searchQuery : String = ""
) : PagingSource<Int, Movie>() {


   private val options = movieRequestOptionsMapper.map(filterState)
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {

            val page = params.key ?: 1
            val moviesResponse = if(searchQuery.isNotBlank()) {
                movieService.search(page, searchQuery)
            }
            else {
                movieService.fetchMovies(page,options)
            }

            val movies = moviesResponse.movies.map(movieMapper::map)

            LoadResult.Page(
                data = movies,
                prevKey = if(page <= 1) null else page - 1,
                nextKey = if(page >= moviesResponse.totalPages) null else page + 1
            )
        }
        catch (exception : Exception) {
            LoadResult.Error(exception)
        }
    }


}