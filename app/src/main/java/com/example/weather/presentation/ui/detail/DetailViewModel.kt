package com.example.weather.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.domain.useCase.WeatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import taptap.pub.handle
import javax.inject.Inject

class DetailViewModel @Inject constructor( private  val weatherUseCase :WeatherUseCase) : ViewModel() {
    private val listWeather = MutableLiveData<List<CurrentWeather>>()
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