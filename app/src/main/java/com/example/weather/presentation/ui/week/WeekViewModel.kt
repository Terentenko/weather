package com.example.weather.presentation.ui.week

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.useCase.WeatherUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import taptap.pub.handle
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor ( private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val listWeather = MutableLiveData<List<CurrentWeather>>()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val listCurrentWeather: LiveData<List<CurrentWeather>> = listWeather


    fun getWeekWeather(latLng: LatLng) {
        dataLoading.value = true
        viewModelScope.launch {
            weatherUseCase.getWeekWeatherForecast(
                latLng = latLng
            ).handle(
                success = { listWeather.value = it.toList() },
                error = { Log.d("test", "Error----> $it") }
            )
            dataLoading.postValue(false)
        }

    }
}