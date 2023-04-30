package com.example.weather.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CurrentWeather(
    val date: Long,
    val city: City,

    val sunrise: Long,
    val sunset: Long,
    val winDeg: Int,
    val winGust: Double,
    val winSpeed: Double,
    val clouds: Int,

    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMax: Double,
    val tempMin: Double,
    val seaLevel: Int,
    val grndLevel: Int


) {
    companion object {


        fun turnUTCInto(timeMillis: Long, format: String): String =
            SimpleDateFormat(format, Locale.getDefault()).format(Date(timeMillis * 1000L))

    }
}