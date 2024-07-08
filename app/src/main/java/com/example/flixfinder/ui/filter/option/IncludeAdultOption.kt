package com.example.flixfinder.ui.filter.option

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAdultContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.example.flixfinder.R
import com.example.flixfinder.ui.filter.FilterSectionTitle
import com.example.flixfinder.ui.filter.FilterState

class IncludeAdultOption(override val defaultValue: Boolean) : FilterOption<Boolean> {


    override var currentValue: Boolean = defaultValue

    override fun modifyFilterState(filterState: FilterState) = filterState.copy(includeAdult = currentValue)

    @Composable
    override fun Render(onChanged: () -> Unit) {
        val isChecked = remember(defaultValue) {
            mutableStateOf(currentValue)
        }

        val onClick = {
            currentValue = currentValue.not()
            isChecked.value = currentValue
            onChanged()
        }

        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            FilterSectionTitle(painter = rememberVectorPainter(image = Icons.Default.NoAdultContent), title = R.string.include_adult)

            Switch(checked = isChecked.value, onCheckedChange = { onClick() })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RenderPrev() {
    val temp = IncludeAdultOption(false)
    temp.Render {

    }

}

