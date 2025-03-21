package com.example.flixfinder.ui.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flixfinder.R


@Composable
fun FilterBottomSheetContent(filterState: FilterState, onFilterStateChanged : (FilterState) -> Unit) {

    Spacer(modifier = Modifier.padding(top = 4.dp))
    filterState.toFilterOptions().forEach {filterOption ->
        filterOption.Render {
            val newState = filterOption.modifyFilterState(filterState)
            onFilterStateChanged(newState)
        }
    }

}


@Composable
fun FilterHeader(onHideClicked : () -> Unit, onResetClicked : (() -> Unit)? = null) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.primary
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onHideClicked) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_content_description)
                    )
                }

                Text(
                    text = stringResource(id = R.string.title_filter_bottom_sheet),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                    )

            }
            
            if(onResetClicked != null) {
                IconButton(
                    onClick = { onResetClicked() },
                    modifier = Modifier.size(50.dp)
                    ) {
                    
                    Text(
                        text = stringResource(id = R.string.reset),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }

}


@Preview(showSystemUi = true)
@Composable
private fun FilterHeaderPrev() {
    
    FilterHeader(onHideClicked = { /*TODO*/ },){

    }
    
}