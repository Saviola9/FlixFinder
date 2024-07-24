package com.example.flixfinder.ui.filter.option

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flixfinder.R
import com.example.flixfinder.ui.filter.FilterGrid
import com.example.flixfinder.ui.filter.FilterRadioItem
import com.example.flixfinder.ui.filter.FilterSectionDivider
import com.example.flixfinder.ui.filter.FilterSectionTitle
import com.example.flixfinder.ui.filter.FilterState
import kotlinx.serialization.Serializable

class SortOrderOption(override val defaultValue: SortByOrder) : FilterOption<SortByOrder> {

    override var currentValue = defaultValue
    override fun modifyFilterState(filterState: FilterState): FilterState = filterState.copy(sortOrder = currentValue)

    @Composable
    override fun Render(onChanged: () -> Unit) {

        val sortOrderState = remember(defaultValue) {
            mutableStateOf(currentValue)
        }


            FilterSectionTitle(painter = rememberVectorPainter(image = Icons.Default.SwapVert), title = R.string.sort_order)

            val sortOrderValues = SortByOrder.entries.toList()
            FilterGrid(listItems = sortOrderValues) { index, modifier ->
                val sortOrder = sortOrderValues[index]
                val selected = sortOrderState.value == sortOrder

                FilterRadioItem(title = stringResource(id = sortOrder.titleResId), selected = selected, modifier = modifier) {

                    currentValue = sortOrder
                    sortOrderState.value = currentValue
                    onChanged()

                }
            }

            FilterSectionDivider()



    }
}


@Serializable
enum class SortByOrder(@StringRes val titleResId: Int, val order: String) {
    DESCENDING(R.string.sort_order_descending, "desc"),
    ASCENDING(R.string.sort_order_ascending, "asc"),
}

@Preview(showSystemUi = true)
@Composable
private fun RenderPrev() {

    val sortOrder = SortByOrder.ASCENDING
    val sortOrderOption = SortOrderOption(sortOrder)

    sortOrderOption.Render {


    }

}