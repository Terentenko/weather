package com.example.weather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.domain.model.City

class SharedViewModel : ViewModel() {
    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city

    fun setCiti(city: City) {
        _city.value = city
    }

    private val _permission = MutableLiveData<Boolean>()
    val permission: LiveData<Boolean> get() = _permission

    fun setPermission(permission: Boolean) {
        _permission.value = permission
    }
}