package com.example.weather.data.okHttp.jsonObject.currentWeather


import com.example.weather.data.okHttp.jsonObject.*

data class ResponseCurrentWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Long,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: SysDay,
    val timezone: Long,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    val snow: Snow?
)