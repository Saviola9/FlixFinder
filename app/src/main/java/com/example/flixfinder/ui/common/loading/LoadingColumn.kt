package com.example.flixfinder.ui.common.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingColumn(title : String, modifier: Modifier = Modifier) {
    
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp)
        )
    }
    
}


@Preview(showSystemUi = true)
@Composable
private fun LoadingColumnPrev() {
    
    LoadingColumn(title = "Please Wait...")
    
}