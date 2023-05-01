package com.example.weather.presentation.ui.week

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

class WeekViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryWeather =
        RepositoryWeatherImpl(
            RepositoryNetworkImpl(),
            RepositoryDataBaseImpl(context = application),
            RepositorySharedPref(context = application)
        )
    private val weatherUseCase = WeatherUseCase(repositoryWeather = repositoryWeather)
    private val listWeather = MutableLiveData<List<CurrentWeather>>().apply {
        //   value = "This is dashboard Fragment"
    }
    val listCurrentWeather: LiveData<List<CurrentWeather>> = listWeather


    fun getWeekWeather(latLng: LatLng) {
        viewModelScope.launch {
            weatherUseCase.getWeekWeatherForecast(
                latLng = latLng
            ).handle(
                success = { listWeather.value = it.toList() },
                error = { Log.d("test", "Error----> $it") }
            )

        }

    }
}