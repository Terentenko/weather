package com.example.weather.data.okHttp.jsonObject.fiveDayWeatherForecast

import com.example.weather.data.okHttp.jsonObject.Coord

data class City(
    val coord: Coord,
    val country: String,
    val id: Int, ///
    val name: String,
    val population: Int,
    val sunrise: Long,
    val sunset: Long,
    val timezone: Long
)