package com.example.weather.data.repository

import android.content.Context
import com.example.weather.data.dataBase.AppDataBase
import com.example.weather.data.dataBase.entity.CityDbEntity
import com.example.weather.data.dataBase.entity.CurrentWeatherDbEntity
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.model.DataException
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import taptap.pub.Reaction
import javax.inject.Inject

class RepositoryDataBaseImpl @Inject constructor(@ApplicationContext context: Context) :
    RepositoryDataBase {
    private val dataBase = AppDataBase.getInstance(context)
    private val cityDao = dataBase.getCitiDao()
    private val weatherDao = dataBase.getCurrentWeatherDao()

    override suspend fun saveWeather(weather: List<CurrentWeather>) =
        withContext(Dispatchers.IO) {
            cityDao.addCity( CityDbEntity.fromCityDbEntity(weather[0].city))
            weather.forEach {
                weatherDao.addCurrentWeather(
                    currentWeatherDbEntity = CurrentWeatherDbEntity.fromCurrentWeather(
                        currentWeather = it

                    )
                )
            }
        }

    override suspend fun getWeather(city: City): Reaction<List<CurrentWeather>> =
        withContext(Dispatchers.IO) {
            Reaction.on {
                cityDao.getCitiByLocationName(cityName = city.name)?.let { cityDataBase ->
                    weatherDao.getListCurrentWeather(cityName = city.name)
                        ?.map { it.toCurrentWeather(city = cityDataBase) }
                } ?: listOf()

            }
        }


    override suspend fun dropWeather() {
        withContext(Dispatchers.IO) {
            weatherDao.deleteAll()
        }
    }

    override suspend fun getCitiByLocationName(name: String): Reaction<City> =
        withContext(Dispatchers.IO) {
            Reaction.on {
                cityDao.getCitiByLocationName(cityName = name)?.toCity() ?: throw DataException(message = "not Citi")
            }
        }

    override suspend fun getCityByCoordinates(latLng: LatLng): Reaction<City> =
        withContext(Dispatchers.IO) {
            Reaction.on {
                cityDao.getCityByCoordinates(
                    lat = latLng.latitude,
                    lon = latLng.longitude
                )?.toCity() ?: throw DataException(message = "not Citi")
            }
        }
}