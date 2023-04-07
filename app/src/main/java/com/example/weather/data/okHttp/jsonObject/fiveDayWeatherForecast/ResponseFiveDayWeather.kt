package com.example.weather.data.okHttp.jsonObject.fiveDayWeatherForecast

data class ResponseFiveDayWeather(
    val city: City,
    val cnt: Int,
    val cod: String,//
    val list: List<WeatherDay>,
    val message: Int ///
)