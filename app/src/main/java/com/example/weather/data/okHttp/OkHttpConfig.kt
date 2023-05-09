package com.example.weather.data.okHttp

import com.example.weather.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpConfig @Inject constructor(
    val client: OkHttpClient,
    val gson: Gson
) {
     val apikey: String = BuildConfig.OPEN_WEATHER_API_KEY
     val url: String = "https://api.openweathermap.org/"
}