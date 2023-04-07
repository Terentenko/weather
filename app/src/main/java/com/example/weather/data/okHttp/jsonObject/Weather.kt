package com.example.weather.data.okHttp.jsonObject

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)