package com.example.weather.data.okHttp.jsonObject.geocoding.coordinates

import com.example.weather.data.okHttp.jsonObject.LocalNames

data class ResponseCoordinatesByLocationNameItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)