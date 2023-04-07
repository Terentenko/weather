package com.example.weather.data.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng


interface RepositoryDataBase {
    suspend fun saveWeather(weather: List<CurrentWeather>)
    suspend fun getWeather(city: City):List<CurrentWeather>
    suspend fun dropWeather()
    suspend fun getCitiByLocationName(name:String):City?
    suspend fun getCityByCoordinates(latLng: LatLng):City?

}