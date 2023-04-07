package com.example.weather.data.okHttp.jsonObject.fiveDayWeatherForecast

import com.example.weather.data.okHttp.jsonObject.*

data class WeatherDay(
    val clouds: Clouds,
    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)