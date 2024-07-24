package com.example.flixfinder.ui.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flixfinder.R
import com.example.flixfinder.ui.common.loading.LoadingColumn
import com.example.flixfinder.ui.filter.FilterBottomSheetContent
import com.example.flixfinder.ui.filter.FilterHeader
import com.example.flixfinder.ui.filter.FilterViewModel
import com.example.flixfinder.ui.main.LocalDarkTheme
import com.example.flixfinder.ui.settings.SettingsContent
import kotlinx.coroutines.launch






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen() {

    val filterViewModel = hiltViewModel<FilterViewModel>()
    val sheetState = rememberModalBottomSheetState()
    val filterState = filterViewModel.filterState.collectAsStateWithLifecycle().value
    val coroutineScope = rememberCoroutineScope()
  /*  val hideFilterBottomSheet : () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
        }
    }*/
    var showBottomSheet by remember { mutableStateOf(false) }

    var showSettingsDialog by remember { mutableStateOf(false) }

    var searchQuery = remember { mutableStateOf("") }

    val moviesViewModel = hiltViewModel<MoviesViewModel>()



    var isSearching = remember { mutableStateOf(false) }

    Scaffold(
     modifier = Modifier.statusBarsPadding(),
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 16.dp
            ) {

                if(isSearching.value) {

                    FlixFinderSearchBar(
                        searchQuery = searchQuery.value,
                        isSearching = isSearching.value,
                        onQueryChange = { query ->
                            searchQuery.value = query
                            moviesViewModel.onSearch(query)
                        },
                        onSearch = { isSearching.value = !isSearching.value },
                        onActiveChange = {
                            isSearching.value = it
                        },
                        modifier = Modifier.clickable {
                            searchQuery.value = ""
                        }
                    )
                }
                else {

                    FlixFinderAppBar(
                        onSettingsClicked = { showSettingsDialog = true },
                        onSearchClicked = { isSearching.value = true}
                    )
                }









            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = searchQuery.value.isBlank()) {
                FloatingActionButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .navigationBarsPadding()
                    ,
                    onClick = { showBottomSheet = true},
                    content = {
                        val color = if (LocalDarkTheme.current.value) {
                            MaterialTheme.colorScheme.surface
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                        val tint = animateColorAsState(targetValue = color, label = "FabTint").value

                        Image(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = stringResource(id = R.string.title_filter_bottom_sheet),
                            colorFilter = ColorFilter.tint(tint)
                        )
                    }
                )
            }
        },
        content = {contentPadding ->


            MoviesGrid(moviesViewModel, modifier = Modifier.padding(contentPadding))

            if(showSettingsDialog) {
                SettingsContent(onDialogDismissed = { showSettingsDialog = false})
            }

            if(showBottomSheet)
            {

                ModalBottomSheet(
                    sheetState = sheetState,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    scrimColor = Color.DarkGray.copy(alpha = 0.7f),
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    modifier = Modifier.padding(contentPadding)
                ) {

                    val onResetClicked = if (filterState == null) null else filterViewModel::onResetClicked
                    FilterHeader(
                        onHideClicked = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                        onResetClicked = onResetClicked
                        )

                    if(filterState == null) {
                        LoadingColumn(
                            title = stringResource(id = R.string.loading_filter_options),
                            modifier = Modifier.fillMaxHeight(0.4f)
                        )
                    }
                    else {
                        FilterBottomSheetContent(
                            filterState = filterState,
                            onFilterStateChanged = filterViewModel::onFilterStateChanged
                        )
                    }



                }
            }



        }

    )


}

@Composable
private fun FlixFinderAppBar(onSettingsClicked: () -> Unit, onSearchClicked : () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val isDarkTheme = LocalDarkTheme.current
    val iconTint = animateColorAsState(if(isDarkTheme.value) colors.onSurface else colors.primary, label = "appIconTint").value

    Row(
       modifier = Modifier
           .fillMaxWidth()
           .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(onClick = onSettingsClicked) {

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings_content_description),
                tint = iconTint
                )
        }

        Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_name) ,
            tint = iconTint,
            modifier = Modifier.size(82.dp)
        )

        /*val icon = if(isDarkTheme.value) Icons.Default.NightsStay else Icons.Default.WbSunny

        IconButton(onClick = { isDarkTheme.value = !isDarkTheme.value}) {
            val contentDescription = if(isDarkTheme.value) {
                R.string.light_theme_content_description
            }
            else
            {
                R.string.dark_theme_content_description
            }

            Icon(imageVector = icon, contentDescription = stringResource(id = contentDescription), tint = iconTint)
        }*/

        IconButton(onClick = onSearchClicked) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_movies),
                tint = iconTint
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlixFinderSearchBar(searchQuery : String, isSearching : Boolean, onQueryChange : (String) -> Unit, onSearch : (String) -> Unit,  onActiveChange : (Boolean) -> Unit, modifier: Modifier) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = isSearching,
        onActiveChange = onActiveChange,
        colors = SearchBarDefaults.colors(
            inputFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
            )
        ),
        shape = RoundedCornerShape(50),
        placeholder = { Text(text = stringResource(id = R.string.search_movies), color = Color.Gray)},
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null)},
        trailingIcon = {
            AnimatedVisibility(visible = searchQuery.isNotEmpty()) {

                Icon(
                    imageVector = Icons.Default.HighlightOff,
                    contentDescription = null,
                    modifier = modifier
                )
            }
        },

        ) {

    }
}