package com.example.weather.domain.model

import com.google.android.gms.maps.model.LatLng

data class City(
    val name:String,
    val latLng: LatLng
)
