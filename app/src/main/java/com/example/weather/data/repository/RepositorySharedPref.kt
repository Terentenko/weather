package com.example.weather.data.repository

import android.content.Context
import com.example.weather.domain.model.City
import com.google.android.gms.maps.model.LatLng


class RepositorySharedPref(context: Context) {
    private val sharedPreference =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getSelectedCity() =
        City(
            name = sharedPreference.getString(
                CITY_NAME,
                "Kyiv"
            )!!,
            latLng = LatLng(
                sharedPreference.getFloat(CITY_LAT,50.4547F).toDouble(),
                sharedPreference.getFloat(CITY_LNG,30.5238F).toDouble()
            )
        )


    fun saveSelectedCity(city: City): Boolean {
        val editor = sharedPreference.edit()
        editor.putString(CITY_NAME, city.name)
        editor.putFloat(CITY_LNG, city.latLng.longitude.toFloat())
        editor.putFloat(CITY_LAT, city.latLng.latitude.toFloat())
        return editor.commit()

    }

    companion object {
        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val CITY_NAME = "city_name"
        const val CITY_LAT = "city_lat"
        const val CITY_LNG = "city_lng"
    }
}