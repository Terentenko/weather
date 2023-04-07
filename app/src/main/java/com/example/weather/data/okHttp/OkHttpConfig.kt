package com.example.weather.data.okHttp

import com.example.weather.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient

class OkHttpConfig(
    val apikey:String= BuildConfig.OPEN_WEATHER_API_KEY,
    val url:String="http://api.openweathermap.org/",
    val client: OkHttpClient,
    val gson: Gson
    )
