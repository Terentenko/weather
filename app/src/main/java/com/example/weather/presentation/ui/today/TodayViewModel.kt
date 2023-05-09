package com.example.weather.presentation.ui.today

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
class TodayViewModel @Inject constructor( private val weatherUseCase:WeatherUseCase) : ViewModel() {


    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _currentWeatherMutableLiveData = MutableLiveData<List<CurrentWeather>>()
    val currentWeather: LiveData<List<CurrentWeather>> = _currentWeatherMutableLiveData

    fun getWeather(latLng: LatLng) {
        viewModelScope.launch {
            dataLoading.value=true
            weatherUseCase.getCurrentWeatherData(
                latLng = latLng
            ).handle(
                success = { _currentWeatherMutableLiveData.value = it },
                error = { Log.d("test", "Error----> $it")

                }
            )
            dataLoading.postValue(false)
        }

    }

}