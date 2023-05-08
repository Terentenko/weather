package com.example.weather.data.mappers


import com.example.weather.data.okHttp.jsonObject.currentWeather.ResponseCurrentWeather
import com.example.weather.data.okHttp.jsonObject.fiveDayWeatherForecast.ResponseFiveDayWeather
import com.example.weather.data.okHttp.jsonObject.geocoding.coordinates.ResponseCoordinatesByLocationName
import com.example.weather.data.okHttp.jsonObject.geocoding.name.ResponseNameByLocstionCoordinates
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng



fun ResponseCoordinatesByLocationName.toVolumeList(): List<City> =
    this.map {
        City(
            name = it.name,
            latLng = LatLng(it.lat, it.lon)
        )
    }

fun ResponseNameByLocstionCoordinates.toVolumeList(): List<City> =
    this.map {
        City(
            name = it.name,
            latLng = LatLng(it.lat, it.lon)
        )
    }

fun ResponseCurrentWeather.toVolumeList(): CurrentWeather =
    CurrentWeather(
        date = dt,
        city = City(
            name = this.name,
            latLng = LatLng(this.coord.lat, this.coord.lon)
        ),
        sunrise = this.sys.sunrise,
        sunset = this.sys.sunset,
        winDeg = this.wind.deg,
        winGust = this.wind.gust,
        winSpeed = this.wind.speed,
        clouds = this.weather[0].icon,
        temp = this.main.temp,
        feelsLike = this.main.feels_like,
        pressure = this.main.pressure,
        humidity = this.main.humidity,
        tempMax = this.main.temp_max,
        tempMin = this.main.temp_min,
        seaLevel = this.main.sea_level,
        grndLevel = this.main.grnd_level

    )

fun ResponseFiveDayWeather.toVolumeSet(): Set<CurrentWeather> =
    this.list.map { CurrentWeather(
        date =it.dt,
         city = City(
            name = city.name,
            latLng = LatLng(city.coord.lat, city.coord.lon)
        ),
        sunrise = city.sunrise,
        sunset = city.sunset,
        winDeg = it.wind.deg,
        winGust = it.wind.gust,
        winSpeed = it.wind.speed,
        clouds = it.weather[0].icon,
        temp = it.main.temp,
        feelsLike = it.main.feels_like,
        pressure = it.main.pressure,
        humidity = it.main.humidity,
        tempMax = it.main.temp_max,
        tempMin = it.main.temp_min,
        seaLevel = it.main.sea_level,
        grndLevel = it.main.grnd_level
    ) }.toSet()

