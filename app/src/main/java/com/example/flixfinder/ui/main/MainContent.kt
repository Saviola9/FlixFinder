package com.example.flixfinder.ui.main

import androidx.compose.runtime.Composable

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flixfinder.ui.movies.MoviesScreen
import com.example.flixfinder.ui.navigation.Screen


val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller") }
val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }

@Composable
fun MainContent() {

    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.MOVIES.route ) {

        composable(Screen.MOVIES.route) {
          MoviesScreen()
        }


    }

}