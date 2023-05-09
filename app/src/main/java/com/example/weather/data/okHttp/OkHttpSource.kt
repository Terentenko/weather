package com.example.weather.data.okHttp

import com.example.weather.data.okHttp.jsonObject.currentWeather.ResponseCurrentWeather
import com.example.weather.data.okHttp.jsonObject.fiveDayWeatherForecast.ResponseFiveDayWeather
import com.example.weather.data.okHttp.jsonObject.geocoding.coordinates.ResponseCoordinatesByLocationName
import com.example.weather.data.okHttp.jsonObject.geocoding.name.ResponseNameByLocstionCoordinates
import com.google.android.gms.maps.model.LatLng
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpSource @Inject constructor(private val config: OkHttpConfig) : BaseOkHttpSource(config) {
    suspend fun getCoordinatesByLocationName(name: String): ResponseCoordinatesByLocationName {
        val request = Request.Builder()
            .get()
            .url("${config.url}geo/1.0/direct?q=${name}&appid=${config.apikey}")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse()
    }

    suspend fun getLocationNameByCoordinates(latLng: LatLng, limit: Int = 3): ResponseNameByLocstionCoordinates {
        val request = Request.Builder()
            .get()
            .url("${config.url}reverse?lat=${latLng.latitude}&lon=${latLng.longitude}&limit=${limit}&appid=${config.apikey}")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse()
    }

    suspend fun getCurrentWeatherData(latLng: LatLng): ResponseCurrentWeather {
        val request = Request.Builder()
            .get()
            .url("${config.url}data/2.5/weather?lat=${latLng.latitude}&lon=${latLng.longitude}&appid=${config.apikey}&units=metric")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse()
    }

    suspend fun getWeekWeatherForecast(latLng: LatLng): ResponseFiveDayWeather {
        val request = Request.Builder()
            .get()
            .url("${config.url}data/2.5/forecast?lat=${latLng.latitude}&lon=${latLng.longitude}&appid=${config.apikey}&units=metric")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse()
    }


}

