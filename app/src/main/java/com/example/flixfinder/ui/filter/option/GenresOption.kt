package com.example.flixfinder.ui.filter.option

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flixfinder.R
import com.example.flixfinder.data.remote.Genre
import com.example.flixfinder.ui.filter.FilterSectionDivider
import com.example.flixfinder.ui.filter.FilterSectionTitle
import com.example.flixfinder.ui.filter.FilterState
import com.example.flixfinder.ui.filter.genres.GenreUiModel

typealias GenresFilterOption = Pair<List<GenreUiModel>, MutableList<Int>>
class GenresOption(override val defaultValue: GenresFilterOption) : FilterOption<GenresFilterOption> {

    override var currentValue = defaultValue

    private val selectedGenreIds
        get() = currentValue.second

    override fun modifyFilterState(filterState: FilterState): FilterState = filterState.copy(selectedGenreIds = currentValue.second)

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render(onChanged: () -> Unit) {
        val (genreUiModels, selectedGenreIds) = currentValue

        FilterSectionTitle(painter = rememberVectorPainter(image = Icons.Default.Category), title = R.string.genres)

        FlowRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            genreUiModels.forEach { genreUiModel ->

                val genreId = genreUiModel.genre.id
                GenreChip(uiModel = genreUiModel) {selected ->
                    selectedGenreIds.removeAll{ it == genreId}

                    if(selected) {

                        selectedGenreIds.add(genreId)
                    }

                    currentValue = currentValue.copy(second = selectedGenreIds)
                    onChanged()

                }

            }
        }

        FilterSectionDivider()

    }

    @Composable
    private fun GenreChip(uiModel: GenreUiModel, onClicked : (Boolean)->Unit){

        val colors = listOf(uiModel.primaryColor, uiModel.secondaryColor)
        val shape = RoundedCornerShape(50)


        var selected by remember(uiModel.genre.id, selectedGenreIds) {
            mutableStateOf(uiModel.genre.id in currentValue.second)
        }

        val shadow = animateDpAsState(targetValue = if(selected) 8.dp else 4.dp ).value

        val animatedColors = List(colors.size) {index ->

            animateColorAsState(targetValue = if(selected) colors[index] else colors[index].copy(alpha = 0f)).value

        }

        val scale = animateFloatAsState(targetValue = if(selected) 1.1f else 1f).value

        val modifier = Modifier
            .scale(scale)
            .shadow(shadow, shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(2.dp, Brush.horizontalGradient(colors), shape)
            .background(Brush.horizontalGradient(animatedColors), shape)
            .selectable(selected,
                onClick = {
                    selected = selected.not()
                    onClicked(selected)
                }
            )
            .padding(horizontal = 10.dp, vertical = 3.dp)

        Text(text = uiModel.genre.name.orEmpty(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier
        )



    }

}


@Preview(showSystemUi = true)
@Composable
private fun GenrePrev() {
    val g1 = GenreUiModel(Genre(name = "Action" , id = 1))
    val g2 = GenreUiModel(Genre(name = "Comedy" , id = 2))
    val g3 = GenreUiModel(Genre(name = "Drama" , id = 3))
    val genresFilterOption = GenresFilterOption(listOf( g1, g2, g3 ), mutableListOf(1,2,3))
    val g4 = GenreUiModel(Genre(name = "Fantasy" , id = 4))
    val genresOption = GenresOption(genresFilterOption)


    Box(modifier = Modifier.fillMaxHeight(),
    )
    {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.align(Alignment.Center)
        ) {

            genresOption.Render {

            }

        }


    }



}

