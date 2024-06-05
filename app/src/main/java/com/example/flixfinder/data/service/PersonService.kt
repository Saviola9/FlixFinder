package com.example.flixfinder.data.service

import com.example.flixfinder.data.remote.ProfileResponse

interface PersonService {

    suspend fun profile(id : Int) : ProfileResponse
}