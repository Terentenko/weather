package com.example.weather.data.dataBase.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weather.domain.model.City
import com.google.android.gms.maps.model.LatLng

@Entity(
    tableName = "city",
    indices = [
        Index("name"),
        Index("lat"),
        Index("lon")
    ]
)
data class CityDbEntity(
    @PrimaryKey val name: String,
    val lat: Double,
    val lon: Double
) {
    fun toCity() = City(
        name = name,
        latLng = LatLng(lat, lon)
    )

    companion object {
        fun fromCityDbEntity(city: City) = CityDbEntity(
            name = city.name,
            lat = city.latLng.latitude,
            lon = city.latLng.longitude

        )
    }
}


