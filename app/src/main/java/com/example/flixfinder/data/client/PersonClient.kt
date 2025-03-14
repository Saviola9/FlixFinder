package com.example.flixfinder.data.client

import com.example.flixfinder.data.remote.ProfileResponse
import com.example.flixfinder.data.service.PersonService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PersonClient(private val httpClient: HttpClient) : PersonService {

    override suspend fun profile(id: Int): ProfileResponse = httpClient.get("person/$id").body()
}