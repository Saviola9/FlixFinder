package com.example.flixfinder.ui.filter

import com.example.flixfinder.ui.filter.genres.GenreUiModel
import com.example.flixfinder.ui.filter.option.SortBy
import com.example.flixfinder.ui.filter.option.SortByOrder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class FilterState(
    @SerialName("sort_order") val sortOrder : SortByOrder = SortByOrder.DESCENDING,
    @SerialName("sort_by") val sortBy: SortBy = SortBy.POPULARITY,
    @SerialName("includeAdult") val includeAdult : Boolean = false,
    @SerialName("selected_genre_ids") val selectedGenreIds :List<Int> = emptyList(),
    @Transient val genres : List<GenreUiModel> = emptyList(),
)

