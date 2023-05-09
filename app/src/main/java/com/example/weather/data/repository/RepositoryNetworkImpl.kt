package com.example.weather.data.repository

import com.example.weather.data.mappers.toVolumeList
import com.example.weather.data.mappers.toVolumeSet
import com.example.weather.data.okHttp.OkHttpSource
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import taptap.pub.Reaction
import javax.inject.Inject

class RepositoryNetworkImpl@Inject constructor(private val okHttpSource: OkHttpSource) :
    RepositoryNetwork {


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