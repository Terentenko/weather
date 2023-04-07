package com.example.weather.data.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng

class RepositoryDataBaseImpl:RepositoryDataBase {
    override suspend fun saveWeather(weather: List<CurrentWeather>) {
        TODO("Not yet implemented")
    }

    override suspend fun getWeather(city: City): List<CurrentWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun dropWeather() {
        TODO("Not yet implemented")
    }

    override suspend fun getCitiByLocationName(name: String): City? {
        TODO("Not yet implemented")
    }

    override suspend fun getCityByCoordinates(latLng: LatLng): City? {
        TODO("Not yet implemented")
    }
}