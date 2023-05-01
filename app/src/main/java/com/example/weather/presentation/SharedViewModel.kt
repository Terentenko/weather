package com.example.weather.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repository.RepositoryDataBaseImpl
import com.example.weather.data.repository.RepositoryNetworkImpl
import com.example.weather.data.repository.RepositorySharedPref
import com.example.weather.data.repository.RepositoryWeatherImpl
import com.example.weather.domain.model.City
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryWeather =
        RepositoryWeatherImpl(
            RepositoryNetworkImpl(),
            RepositoryDataBaseImpl(context = application),
            RepositorySharedPref(context = application)
        )
    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city

    init {
        getSelectedCity()
    }


    fun setCiti(city: City) {
        _city.value = city
    }

    private val _permission = MutableLiveData<Boolean>()
    val permission: LiveData<Boolean> get() = _permission

    fun setPermission(permission: Boolean) {
        _permission.value = permission
    }

    private fun getSelectedCity() {
        viewModelScope.launch {
            _city.value = repositoryWeather.getSelectedCity()

        }

    }
}