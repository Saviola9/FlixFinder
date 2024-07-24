package com.example.flixfinder.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.flixfinder.data.service.MovieService
import com.example.flixfinder.ui.moviedetail.credits.Credits
import com.example.flixfinder.ui.moviedetail.credits.CreditsMapper
import com.example.flixfinder.ui.moviedetail.image.Image
import com.example.flixfinder.ui.moviedetail.image.ImageMapper
import com.example.flixfinder.ui.navigation.ARG_MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieService: MovieService,
    private val movieDetailMapper: MovieDetailMapper,
    private val creditsMapper: CreditsMapper,
    private val imageMapper: ImageMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState : StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        val movieId : Int = savedStateHandle.get<String>(ARG_MOVIE_ID)!!.toInt()
        fetchMovieDetail(movieId = movieId)
    }

    private fun fetchMovieDetail(movieId: Int) {

        _uiState.value = _uiState.value.copy(loading = true, error = null)


    }


    data class MovieDetailUiState(
        val movieDetail: MovieDetail? = null,
        val credits: Credits = Credits(listOf(), listOf()),
        val images: List<Image> = listOf(),
        val loading: Boolean = false,
        val error: Throwable? = null,
    )
}