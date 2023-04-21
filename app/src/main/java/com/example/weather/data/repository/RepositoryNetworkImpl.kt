package com.example.weather.data.repository

import com.example.weather.data.mappers.toVolumeList
import com.example.weather.data.mappers.toVolumeSet
import com.example.weather.data.okHttp.OkHttpConfig
import com.example.weather.data.okHttp.OkHttpSource
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import taptap.pub.Reaction

class RepositoryNetworkImpl : RepositoryNetwork {
    private val config = OkHttpConfig(
        client = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY) // be for log work okHTTp
        ).build(),
        gson = Gson(),
    )
    private val okHttpSource = OkHttpSource(config)

    override suspend fun getCoordinatesByLocationName(cityName: String): Reaction<List<City>> =
        withContext(Dispatchers.IO) {
            return@withContext Reaction.on {
                okHttpSource.getCoordinatesByLocationName(cityName).toVolumeList()
            }
        }


    override suspend fun getLocationNameByCoordinates(latLng: LatLng): Reaction<List<City>> =
        withContext(Dispatchers.IO) {
            return@withContext Reaction.on {
                okHttpSource.getLocationNameByCoordinates(latLng).toVolumeList()
            }
        }

    override suspend fun getCurrentWeatherData(latLng: LatLng): Reaction<CurrentWeather> =
        withContext(Dispatchers.IO) {
            return@withContext Reaction.on {
                okHttpSource.getCurrentWeatherData(latLng).toVolumeList()
            }
        }

    override suspend fun getWeekWeatherForecast(latLng: LatLng): Reaction<Set<CurrentWeather>> =
        withContext(Dispatchers.IO) {
            return@withContext Reaction.on {
                okHttpSource.getWeekWeatherForecast(latLng).toVolumeSet()
            }
        }
}