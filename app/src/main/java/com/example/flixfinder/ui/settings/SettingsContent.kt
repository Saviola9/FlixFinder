package com.example.flixfinder.ui.settings


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.flixfinder.R
import com.example.flixfinder.ui.common.loading.LoadingRow
import com.example.flixfinder.ui.main.LocalDarkTheme

//const val SETTINGS_DIALOG_TAG = "SettingsDialog"
/*private const val FLAG_ID = "flag"
private const val TICK_ID = "tickIcon"
private const val DROPDOWN_ID = "dropDownIcon"*/


/*private val placeholder = Placeholder(
    width = 2.5.em,
    height = 1.5.em,
    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
)*/


@Composable
fun SettingsContent(onDialogDismissed : () -> Unit) {
    
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    LaunchedEffect(Unit) {
        settingsViewModel.fetchLanguages()
        
    }
    
    val uiState = settingsViewModel.uiState.collectAsStateWithLifecycle().value
    val selectedLanguage = settingsViewModel.selectedLanguage.collectAsStateWithLifecycle(initialValue = Language.default).value
    
    SettingsDialog(uiState, selectedLanguage, settingsViewModel::onLanguageSelected, onDialogDismissed)
    
    
}

@Composable
fun SettingsDialog(
    uiState: SettingsViewModel.UiState, 
    selectedLanguage: Language, 
    onLanguageSelected: (Language) -> Unit, 
    onDialogDismissed: () -> Unit,

) {


    Dialog(onDismissRequest = onDialogDismissed) {



        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
            //  .semantics {  }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {


                LightOrDarkTheme()


                if (uiState.showLoading) {
                    LoadingRow(title = stringResource(id = R.string.fetching_languages))
                } else {
                    LanguageRow(
                        uiState.languages,
                        selectedLanguage,
                        onLanguageSelected = {
                            onLanguageSelected(it)
                            onDialogDismissed()
                        }
                    )

                }





            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LightOrDarkTheme() {

    val isDarkTheme = LocalDarkTheme.current
    val iconTint = animateColorAsState(if(isDarkTheme.value) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary, label = "appIconTint").value
    val icon = if(isDarkTheme.value) Icons.Default.NightsStay else Icons.Default.WbSunny

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(text = stringResource(id = R.string.theme))

        var showDropDown by remember { mutableStateOf(false) }
        /* val selectedValue = if (isDarkTheme.value) "Dark" else "Light"
        val onThemeChange =  { isDarkTheme.value = !isDarkTheme.value }*/
        val onClick = { isDarkTheme.value = !isDarkTheme.value }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {

            ThemeButton(
                enabled = !isDarkTheme.value,
                title = stringResource(id = R.string.dark_theme),
                icon = Icons.Default.NightsStay,
                contentDescription = stringResource(id = R.string.dark_theme_content_description),
                onClick = onClick,
                tint = iconTint
            )

            ThemeButton(
                enabled = isDarkTheme.value,
                title = stringResource(id = R.string.light_theme),
                icon = Icons.Default.WbSunny,
                contentDescription = stringResource(id = R.string.light_theme_content_description),
                onClick = onClick,
                tint = iconTint
            )
        }

    }
            
        /*    IconButton(
                onClick = { isDarkTheme.value = !isDarkTheme.value },
                enabled = !isDarkTheme.value
            ) {

                Text(text = stringResource(id = R.string.dark_theme) )
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = stringResource(id = R.string.dark_theme_content_description),
                    tint = iconTint
                )

            }*/

            /*
        ExposedDropdownMenuBox(expanded = showDropDown, onExpandedChange = {showDropDown = !showDropDown}) {

            TextField(value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropDown) },
                modifier = Modifier.menuAnchor()
                )
            ExposedDropdownMenu(
                expanded = showDropDown,
                onDismissRequest = { showDropDown = false }
            ) {
                DropdownMenuItem(
                    text = {
                            Text(text = stringResource(id = R.string.dark_theme))
                    },
                    onClick = onThemeChange
                )

                DropdownMenuItem(
                    text = {
                            Text(text = stringResource(id = R.string.light_theme))
                    },
                    onClick = onThemeChange,
                    leadingIcon = {

                        val contentDescription = if(isDarkTheme.value) {
                            R.string.light_theme_content_description
                        }
                        else
                        {
                            R.string.dark_theme_content_description
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = contentDescription),
                            tint = iconTint
                        )
                    }
                )
            }



            }

        }*/


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeButton(enabled : Boolean, title : String, icon: ImageVector, contentDescription : String, tint : Color, onClick : () -> Unit) {

    Button(
        modifier = Modifier.padding(8.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = tint
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.size(8.dp))
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
   /* Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        enabled = enabled,

    ) {
    
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            

        }
        
        */
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageRow(
    languages: List<Language>, 
    selectedLanguage: Language, 
    onLanguageSelected: (Language) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var showDropDown by remember { mutableStateOf(false) }
        
        Text(text = stringResource(id = R.string.language))
        
        ExposedDropdownMenuBox(
            expanded = showDropDown,
            onExpandedChange = {showDropDown = !showDropDown},
            modifier = Modifier//.fillMaxHeight(0.4f)
        ) {

            TextField(
                value = selectedLanguage.englishName, 
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropDown)},
                modifier = Modifier.menuAnchor()
                )
            
            ExposedDropdownMenu(expanded = showDropDown,
                onDismissRequest = { showDropDown = false },
                modifier = Modifier.fillMaxHeight(0.4f)
                ) {

                languages.forEach {language ->
                    val selected = language == selectedLanguage

                    DropdownMenuItem(
                        text = { Text(text = language.englishName) },
                        onClick = {
                            onLanguageSelected(language)
                            showDropDown = false
                        },
                        enabled = !selected,
                        leadingIcon = {
                            AsyncImage(
                                model = language.flagUrl,
                                contentDescription = stringResource(id = R.string.flag_content_description, language.englishName),
                                contentScale = ContentScale.FillBounds,

                            )
                        }

                    )

                }
            }
           
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun SettingsPrev() {

    val eng = Language("english", "en","english")
    val ind = Language("hindi", "hi","India")
    val japan = Language("Japanese", "ja","Japan")
    val spain = Language("Spanish", "es","Spain")

    val language = listOf(eng,ind,japan,spain,eng,ind,japan,spain,eng,ind,japan,spain,eng,ind,japan,spain,eng,ind,japan,spain,eng,ind,japan,spain)
    val uiState = SettingsViewModel.UiState(false,language)
    SettingsDialog(uiState = uiState, selectedLanguage = japan, onLanguageSelected = {} ) {

    }
}

