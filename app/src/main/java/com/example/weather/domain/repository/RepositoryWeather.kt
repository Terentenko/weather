package com.example.weather.domain.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng
import taptap.pub.Reaction

interface RepositoryWeather {
    suspend fun getCoordinatesByLocationName(cityName:String): Reaction<List<City>>
    suspend fun getLocationNameByCoordinates(latLng: LatLng):Reaction<List<City>>
    suspend fun getCurrentWeatherData(latLng: LatLng): Reaction<List<CurrentWeather>>
    suspend fun getWeekWeatherForecast(latLng: LatLng): Reaction<Set<CurrentWeather>>
    suspend fun getSelectedCity():City
}