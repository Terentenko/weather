package com.example.weather.data.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng

interface RepositoryNetwork {
    suspend fun getCoordinatesByLocationName(cityName:String): Result<List<City>>
    suspend fun getLocationNameByCoordinates(latLng: LatLng):Result<List<City>>
    suspend fun getCurrentWeatherData(latLng: LatLng): Result<CurrentWeather>
    suspend fun getWeekWeatherForecast(latLng: LatLng): Result<Set<CurrentWeather>>
}