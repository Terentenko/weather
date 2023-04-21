package com.example.weather.data.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng
import taptap.pub.Reaction


interface RepositoryDataBase {
    suspend fun saveWeather(weather: List<CurrentWeather>)
    suspend fun getWeather(city: City): Reaction<List<CurrentWeather>>
    suspend fun dropWeather()
    suspend fun getCitiByLocationName(name:String):Reaction<City>
    suspend fun getCityByCoordinates(latLng: LatLng):Reaction<City>

}