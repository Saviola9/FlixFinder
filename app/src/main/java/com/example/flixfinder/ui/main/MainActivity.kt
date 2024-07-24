package com.example.flixfinder.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.flixfinder.ui.theme.FlixFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme = remember{ mutableStateOf(systemTheme) }
            val navController = rememberNavController()

            FlixFinderTheme(darkTheme = isDarkTheme.value) {

                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalDarkTheme provides isDarkTheme
                ) {
                    MainContent()
                }

            }
        }
    }
}

