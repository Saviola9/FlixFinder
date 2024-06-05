package com.example.flixfinder.util.interceptor

import com.example.flixfinder.ui.settings.LanguageDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor(private val languageDataStore: LanguageDataStore) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val languageCode = runBlocking { languageDataStore.languageCode.first() }
        val url = request.url.newBuilder().addQueryParameter(LANGUAGE, languageCode).build()
        val newRequest = request.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }



    companion object{
        private const val LANGUAGE = "language"
    }
}