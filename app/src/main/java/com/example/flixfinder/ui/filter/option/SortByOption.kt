package com.example.flixfinder.ui.filter.option

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
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

class SortByOption(override val defaultValue: SortBy) : FilterOption<SortBy> {

    override var currentValue: SortBy = defaultValue

    override fun modifyFilterState(filterState: FilterState): FilterState = filterState.copy(sortBy = currentValue)

    @Composable
    override fun Render(onChanged: () -> Unit) {

        val sortByState =  remember(defaultValue) {
            mutableStateOf(currentValue)
        }


            FilterSectionTitle(painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.Sort), title = R.string.sort_by)

            val sortByValues = SortBy.values().toList()
            FilterGrid(listItems = sortByValues) {index , modifier->
                val sortBy = sortByValues[index]
                val selected = sortByState.value == sortBy
                FilterRadioItem(title = stringResource(id = sortBy.titleResId), modifier = modifier, selected = selected) {
                    currentValue = sortBy
                    sortByState.value = sortBy
                    onChanged()

                }

            }

            FilterSectionDivider()




    }
}



@Serializable
enum class SortBy(@StringRes val titleResId : Int, val by : String ){

    POPULARITY(R.string.sort_by_popularity, "popularity"),
    VOTE_COUNT(R.string.sort_by_vote_count, "vote_count"),
    VOTE_AVERAGE(R.string.sort_by_vote_average, "vote_average"),
    RELEASE_DATE(R.string.sort_by_release_date, "release_date"),
    ORIGINAL_TITLE(R.string.sort_by_original_title, "original_title"),
    REVENUE(R.string.sort_by_revenue, "revenue"),
}


@Preview(showSystemUi = true, name = "Run on device to see item animations")
@Composable
private fun VerticalStaggeredGridPreview() {

    val sortBy = SortBy.POPULARITY
    val sortByOption = SortByOption(sortBy)

    sortByOption.Render {

    }

    /*val sortBy = List(80, { Random.nextInt(0,79) })
    FilterGrid(
        listItems = sortBy
    ) { index, modifier ->
        val height = remember(index) { (80..240).random().dp }
        val color = remember(index) { Color.randomColor() }
        Card(
            backgroundColor = color,
            modifier = modifier
                .requiredHeight(height)
                .fillMaxWidth(),
        ) {
            Box(contentAlignment = Alignment.Center) {
                androidx.compose.material3.Text(
                    text = index.toString(),
                    fontSize = 32.sp,
                    color = Color.White,
                )
            }
        }
    }*/
}
