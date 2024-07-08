package com.example.flixfinder.ui.filter.option

import androidx.annotation.StringRes
import com.example.flixfinder.R
import kotlinx.serialization.Serializable

class SortOrderOption {

}


@Serializable
enum class SortByOrder(@StringRes val titleResId: Int, val order: String) {
    DESCENDING(R.string.sort_order_descending, "desc"),
    ASCENDING(R.string.sort_order_ascending, "asc"),
}