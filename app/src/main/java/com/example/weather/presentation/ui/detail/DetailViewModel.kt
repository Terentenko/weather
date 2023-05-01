package com.example.weather.presentation.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repository.RepositoryDataBaseImpl
import com.example.weather.data.repository.RepositoryNetworkImpl
import com.example.weather.data.repository.RepositorySharedPref
import com.example.weather.data.repository.RepositoryWeatherImpl
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.useCase.WeatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import taptap.pub.handle

class DetailViewModel(application: Application) :AndroidViewModel(application) {
    private val repositoryWeather =
        RepositoryWeatherImpl(
            RepositoryNetworkImpl(),
            RepositoryDataBaseImpl(context = application),
            RepositorySharedPref(context = application)
        )
    private val weatherUseCase = WeatherUseCase(repositoryWeather = repositoryWeather)
    private val listWeather = MutableLiveData<List<CurrentWeather>>().apply {

    }
    val listCurrentWeather: LiveData<List<CurrentWeather>> = listWeather


    fun getWeatherDate(date:Int,latLng:LatLng) {
        viewModelScope.launch {
            weatherUseCase.getCurrentWeatherDay (
                latLng = latLng, day = date
            ).handle(
                success = { listWeather.value = it },
                error = { Log.d("test", "Error----> $it") }
            )

        }

    }

}