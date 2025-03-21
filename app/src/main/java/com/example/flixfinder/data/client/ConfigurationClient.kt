package com.example.flixfinder.data.client

import com.example.flixfinder.data.service.ConfigurationService
import com.example.flixfinder.ui.settings.Language
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ConfigurationClient(private val httpClient: HttpClient) : ConfigurationService {
    override suspend fun fetchLanguage(): List<Language> = httpClient.get("configuration/languages").body()
}