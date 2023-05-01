package com.example.weather.data.repository


import com.example.weather.domain.model.City
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.repository.RepositoryWeather
import com.google.android.gms.maps.model.LatLng
import taptap.pub.Reaction
import taptap.pub.fold
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RepositoryWeatherImpl(
    private val repositoryNetwork: RepositoryNetwork,
    private val repositoryDataBase: RepositoryDataBase,
    private  val repositorySharedPref: RepositorySharedPref
) : RepositoryWeather {

    override suspend fun getCoordinatesByLocationName(cityName: String): Reaction<List<City>> =
        repositoryDataBase.getCitiByLocationName(cityName).fold(
            success = { Reaction.on { (listOf(it)) } },
            error = {
                repositoryNetwork.getCoordinatesByLocationName(cityName = cityName)
            }
        )

    override suspend fun getLocationNameByCoordinates(latLng: LatLng): Reaction<List<City>> =
        repositoryDataBase.getCityByCoordinates(latLng = latLng).fold(
            success = { Reaction.on { (listOf(it)) } },
            error = {
                repositoryNetwork.getLocationNameByCoordinates(latLng = latLng)
            }
        )

    override suspend fun getCurrentWeatherData(latLng: LatLng): Reaction<List<CurrentWeather>> {

        val currentDate =
            SimpleDateFormat("dd", Locale.getDefault()).format(Date(System.currentTimeMillis()))
                .toInt()
        return Reaction.on {
            getWeekWeatherWithNetworkAndSaveToDB(latLng = latLng).filter {
                CurrentWeather.turnUTCInto(
                    it.date,
                    "dd"
                ).toInt() == currentDate
            }

        }
    }


    override suspend fun getWeekWeatherForecast(latLng: LatLng): Reaction<Set<CurrentWeather>> =
        Reaction.on { getWeekWeatherWithNetworkAndSaveToDB(latLng = latLng) }





    override suspend fun getSelectedCity(): City =
        repositorySharedPref.getSelectedCity()



    private suspend fun getWeekWeatherWithNetworkAndSaveToDB(latLng: LatLng): Set<CurrentWeather> {
        repositoryNetwork.getWeekWeatherForecast(latLng = latLng).fold(
            success = {
                repositoryDataBase.dropWeather() // todo recycling for removal around the city !!!
                repositoryDataBase.saveWeather(it.toList())
                repositorySharedPref.saveSelectedCity(city = it.last().city)
                return it
            },
            error = {
                repositoryDataBase.getCityByCoordinates(latLng = latLng).fold(
                    success = { city ->
                        repositoryDataBase.getWeather(city).fold(
                            success = { return it.toSet() },
                            error = { throw it }
                        )
                    },
                    error = { throw it })
            })
    }
}


