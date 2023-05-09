package com.example.weather.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.City
import com.example.weather.domain.useCase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repositoryWeather: WeatherUseCase) :
    ViewModel() {
    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city
    private val _permission = MutableLiveData<Boolean>()
    val permission: LiveData<Boolean> get() = _permission
    private val _date = MutableLiveData<Int>()
    val date: LiveData<Int> get() = _date

    init {
        getSelectedCity()
    }

    fun setCiti(city: City) {
        _city.value = city
    }

    fun setPermission(permission: Boolean) {
        _permission.value = permission
    }

    fun setDate(date: Int) {
        _date.value = date
    }

    private fun getSelectedCity() {
        viewModelScope.launch {
            _city.value = repositoryWeather.getSelectedCity()

        }

    }
}