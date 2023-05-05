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
                sharedPreference.getString(CITY_LAT,"50.4547f")!!.replace(",", ".").toDouble(),
                sharedPreference.getString(CITY_LNG,"30.5238f")!!.replace(",", ".").toDouble()
            )
        )


    fun saveSelectedCity(city: City): Boolean {
        val editor = sharedPreference.edit()
        editor.putString(CITY_NAME, city.name)
        editor.putString(CITY_LNG, String.format("%.4f", city.latLng.longitude))
        editor.putString(CITY_LAT, String.format("%.4f", city.latLng.latitude))
        return editor.commit()

    }

    companion object {
        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val CITY_NAME = "city_name"
        const val CITY_LAT = "city_lat"
        const val CITY_LNG = "city_lng"
    }
}