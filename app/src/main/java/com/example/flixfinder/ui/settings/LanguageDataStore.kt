package com.example.flixfinder.ui.settings

import com.example.flixfinder.data.local.LocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LanguageDataStore(private val json : Json, private val localDataStore: LocalDataStore){

    val language : Flow<Language> = localDataStore.get(KEY_LANGUAGE)
        .map { languageString ->

            if(languageString != null) {
                json.decodeFromString(languageString)
            }
            else{
                Language.default
            }
        }
        .catch {
            emit(Language.default)
        }

    val languageCode : Flow<String> = language.map { it.iso6391 }

    suspend fun onLanguageSelected(lang : Language) {
        localDataStore.set(KEY_LANGUAGE,json.encodeToString(lang))
    }

    companion object {
        private const val KEY_LANGUAGE = "language"
    }
}