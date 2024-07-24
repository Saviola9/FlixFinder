package com.example.flixfinder.ui.common.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flixfinder.R

@Composable
fun ErrorRow(title : String, modifier: Modifier = Modifier) {
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline, 
            contentDescription = stringResource(id = R.string.error_icon_description),
            modifier = Modifier.size(40.dp)
        )
        
        Text(text = title)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorRowPreview() {
    ErrorRow(title = "ERROR LOADING DATA!!!")
    
}