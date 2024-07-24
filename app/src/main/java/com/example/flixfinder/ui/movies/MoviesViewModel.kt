package com.example.flixfinder.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.flixfinder.data.service.MovieService
import com.example.flixfinder.ui.filter.FilterDataStore
import com.example.flixfinder.ui.filter.FilterState
import com.example.flixfinder.ui.filter.MovieRequestOptionsMapper
import com.example.flixfinder.ui.movies.movie.Movie
import com.example.flixfinder.ui.movies.movie.MovieMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieService: MovieService,
    private val movieMapper: MovieMapper,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper,
    filterDataStore: FilterDataStore
) : ViewModel() {

    private val pager : Pager<Int, Movie> = Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = ::initPagingSource)

    val movies : Flow<PagingData<Movie>> = pager.flow
    val filterStateChanges = MutableSharedFlow<FilterState>()
    private var filterState : FilterState? = null

    val searchQuery = MutableStateFlow("")
    private val _searchQueryChanges = MutableSharedFlow<Unit>()
    val searchQueryChanges = _searchQueryChanges.asSharedFlow()

    init {

        filterDataStore.filterState
            .onEach { filterState ->

                this.filterState = filterState
                filterStateChanges.emit(filterState)
        }
            .launchIn(viewModelScope)

        searchQuery
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .onEach {
                _searchQueryChanges.emit(Unit)
            }
            .launchIn(viewModelScope)
    }



    private fun initPagingSource() = MoviesPagingSource(
        movieService,
        movieMapper,
        movieRequestOptionsMapper,
        filterState,
        searchQuery.value
    )


     fun onSearch(query : String) {

        if(searchQuery.value.isEmpty() && query.isBlank()) return

        if(searchQuery.value.isBlank() && query.length < SEARCH_MINIMUM_LENGTH) return

        searchQuery.tryEmit(query)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val SEARCH_MINIMUM_LENGTH = 3

    }
}