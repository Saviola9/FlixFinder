package com.example.flixfinder.data.service

import com.example.flixfinder.ui.settings.Language

interface ConfigurationService {

    suspend fun fetchLanguage() : List<Language>
}