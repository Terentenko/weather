package com.example.weather.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.domain.model.City
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {

    private val _currentLocationCity = MutableLiveData<City>().apply {
        value = City(
            name = "Kiev",
            latLng = LatLng(
                50.4547,
                30.5238
            )
        ) // Current location is set to India, this will be use case
    }
    val currentLocationCity: LiveData<City> = _currentLocationCity
}