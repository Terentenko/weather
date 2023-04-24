package com.example.weather.presentation.ui.today

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repository.RepositoryDataBaseImpl
import com.example.weather.data.repository.RepositoryNetworkImpl
import com.example.weather.data.repository.RepositoryWeatherImpl
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.useCase.WeatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import taptap.pub.handle

class TodayViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryWeather =
        RepositoryWeatherImpl(
            RepositoryNetworkImpl(),
            RepositoryDataBaseImpl(context = application)
        )
    private val weatherUseCase = WeatherUseCase(repositoryWeather = repositoryWeather)

    private val _currentWeatherMutableLiveData = MutableLiveData<CurrentWeather>().apply {

    }
    val currentWeather: LiveData<CurrentWeather> = _currentWeatherMutableLiveData

    init {
        getWeather(latLng = LatLng(
            50.4547,
            30.5238
        ))
    }

     fun getWeather(latLng: LatLng) {
        viewModelScope.launch {
             weatherUseCase.getCurrentWeatherData(
                latLng = latLng
            ).handle(
                success = { _currentWeatherMutableLiveData.value=it },
                error = { Log.d("test", "Error----> $it") }
            )

        }

    }


}