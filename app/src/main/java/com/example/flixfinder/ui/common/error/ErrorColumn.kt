package com.example.flixfinder.ui.common.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Face
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
fun ErrorColumn(message : String, modifier : Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = stringResource(id = R.string.error_icon_description),
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp)

        )
    }

}


@Preview(showSystemUi = true)
@Composable
private fun ErrorColumnPrev() {
    ErrorColumn(message = "ERROR IN LOADING")
}