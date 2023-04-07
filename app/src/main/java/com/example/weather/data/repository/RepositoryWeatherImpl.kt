package com.example.weather.data.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.repository.RepositoryWeather
import com.google.android.gms.maps.model.LatLng


class RepositoryWeatherImpl(
    private val repositoryNetwork: RepositoryNetwork,
    private val repositoryDataBase: RepositoryDataBase
) : RepositoryWeather {

    override suspend fun getCoordinatesByLocationName(cityName: String): Result<List<City>> =
        repositoryNetwork.getCoordinatesByLocationName(cityName = cityName)

    override suspend fun getLocationNameByCoordinates(latLng: LatLng): Result<List<City>> =
        repositoryNetwork.getLocationNameByCoordinates(latLng = latLng)

    override suspend fun getCurrentWeatherData(latLng: LatLng): Result<CurrentWeather> =
        repositoryNetwork.getCurrentWeatherData(latLng = latLng)

    override suspend fun getWeekWeatherForecast(latLng: LatLng): Result<Set<CurrentWeather>> =
        repositoryNetwork.getWeekWeatherForecast(latLng = latLng)
}
