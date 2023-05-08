package com.example.weather.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class CurrentWeather(
    val date: Long,
    val city: City,

    val sunrise: Long,
    val sunset: Long,
    val winDeg: Int,
    val winGust: Double,
    val winSpeed: Double,
    val clouds: String,

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

        const val URL_ICON_BEGINNING = "https://openweathermap.org/img/wn/"
        const val URL_ICON_END = "@2x.png"
        fun turnUTCInto(timeMillis: Long, format: String): String =
            SimpleDateFormat(format, Locale.getDefault()).format(Date(timeMillis * 1000L))


        fun averageWeather(listCurrentWeather: List<CurrentWeather>): List<CurrentWeather> =
            listCurrentWeather.groupBy {
                turnUTCInto(
                    it.date,
                    "dd"
                ).toInt()
            }.values.toList().map { list ->
                listCurrentWeatherToAverageWeather(list)


            }

        fun listCurrentWeatherToAverageWeather(list: List<CurrentWeather>) =
            CurrentWeather(
                date = list[0].date,
                city = list[0].city,
                sunrise = list[0].sunrise,
                sunset = list[0].sunset,
                winDeg = list.sumOf { it.winDeg } / list.size,
                winGust = list.sumOf { it.winGust } / list.size,
                winSpeed = list.sumOf { it.winSpeed } / list.size,
                clouds = list[0].clouds,
                temp = list.sumOf { it.temp } / list.size,
                feelsLike = list.sumOf { it.feelsLike } / list.size,
                pressure = list.sumOf { it.pressure } / list.size,
                humidity = list.sumOf { it.humidity } / list.size,
                tempMax = list.maxOf { it.tempMax },
                tempMin = list.minOf { it.tempMin },
                seaLevel = list.sumOf { it.seaLevel } / list.size,
                grndLevel = list.sumOf { it.grndLevel } / list.size,

                )


        fun turnUTCIntoDay(timeMillis: Long): String {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.timeInMillis = timeMillis * 1000L
            val daysOfWeek =
                arrayOf("неділя", "понеділок", "вівторок", "середа", "четвер", "п'ятниця", "субота")
            return daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]
        }
    }


}