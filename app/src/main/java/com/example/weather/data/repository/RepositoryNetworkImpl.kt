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

class RepositoryNetworkImpl:RepositoryNetwork {
    private val config = OkHttpConfig(
        client = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY) // be for log work okHTTp
        ).build(),
        gson = Gson(),
    )
    private val okHttpSource = OkHttpSource(config)

    override suspend fun getCoordinatesByLocationName(cityName: String): Result<List<City>> =
        withContext(Dispatchers.IO){
            try {
                val responseCoordinates =
                    okHttpSource.getCoordinatesByLocationName(cityName)

                return@withContext Result.success(
                    responseCoordinates.toVolumeList())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }



    override suspend fun getLocationNameByCoordinates(latLng: LatLng): Result<List<City>> =
        withContext(Dispatchers.IO){
            try {
                val responseName =
                    okHttpSource.getLocationNameByCoordinates(latLng)

                return@withContext Result.success(
                    responseName.toVolumeList())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun getCurrentWeatherData(latLng: LatLng): Result<CurrentWeather> =
        withContext(Dispatchers.IO){
            try {
                val responseWeatherData =
                    okHttpSource.getCurrentWeatherData(latLng)

                return@withContext Result.success(
                    responseWeatherData.toVolumeList())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }

    override suspend fun getWeekWeatherForecast(latLng: LatLng): Result<Set<CurrentWeather>> =
        withContext(Dispatchers.IO){
            try {
                val responseWeekWeather =
                    okHttpSource.getWeekWeatherForecast(latLng)

                return@withContext Result.success(
                    responseWeekWeather.toVolumeSet())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
}