package com.example.weather.data.okHttp.jsonObject.currentWeather

data class SysDay(
    val country: String,
    val id: Int,
    val sunrise: Long,
    val sunset: Long,
    val type: Int
)