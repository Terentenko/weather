package com.example.weather.data.okHttp.jsonObject.geocoding.name

import com.example.weather.data.okHttp.jsonObject.LocalNames

data class ResponseNameByLocationCoordinatesItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String
)