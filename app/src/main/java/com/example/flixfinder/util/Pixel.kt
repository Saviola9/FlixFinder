package com.example.flixfinder.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Int.toDp() : Float {
    val density = LocalDensity.current.density
    return remember(this) { this / density }

}

@Composable
fun Int.dpToPx(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this * density }
}