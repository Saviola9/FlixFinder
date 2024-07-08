package com.example.flixfinder.ui.filter

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun FilterSectionTitle(painter : Painter, @StringRes title : Int) {

    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Icon(painter = painter, contentDescription = null, modifier = Modifier.size(26.dp))
        
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

}


@Composable
fun FilterSectionDivider() = Divider(
    modifier = Modifier.padding(vertical = 4.dp)
)