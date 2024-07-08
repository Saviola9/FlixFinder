package com.example.flixfinder.ui.filter.genres

import com.example.flixfinder.data.remote.Genre
import com.example.flixfinder.util.Mapper
import javax.inject.Inject

class GenreUiModelMapper @Inject constructor() : Mapper<Genre, GenreUiModel> {

    override fun map(input: Genre): GenreUiModel = GenreUiModel(input)
}