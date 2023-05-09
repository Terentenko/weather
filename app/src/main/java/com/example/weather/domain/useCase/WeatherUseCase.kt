package com.example.weather.domain.useCase


import com.example.weather.domain.repository.RepositoryWeather
import com.google.android.gms.maps.model.LatLng


class WeatherUseCase(private val repositoryWeather: RepositoryWeather) {
    suspend fun getCoordinatesByLocationName(cityName: String) =
        repositoryWeather.getCoordinatesByLocationName(cityName = cityName)

    suspend fun getLocationNameByCoordinates(latLng: LatLng) =
        repositoryWeather.getLocationNameByCoordinates(latLng = latLng)

    suspend fun getCurrentWeatherData(latLng: LatLng) =
        repositoryWeather.getCurrentWeatherData(latLng = latLng)

    suspend fun getWeekWeatherForecast(latLng: LatLng) =
        repositoryWeather.getWeekWeatherForecast(latLng = latLng)

    suspend fun getCurrentWeatherDay(latLng: LatLng, day: Int) =
        repositoryWeather.getCurrentWeatherDay(latLng = latLng, day = day)

    suspend fun getSelectedCity() =
        repositoryWeather.getSelectedCity()
}