package com.example.weather.data.dataBase.dao

import androidx.room.*
import com.example.weather.data.dataBase.entity.CityDbEntity

@Dao
interface CityDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityDbEntity)

    @Delete()
    suspend fun dropCityEntity(city: CityDbEntity)

    @Query("SELECT * FROM city WHERE name =:cityName")
    suspend fun getCitiByLocationName(cityName: String): CityDbEntity?

    @Query("SELECT * FROM city WHERE lat = :lat AND lon = :lon")
    suspend fun getCityByCoordinates(lat: Double, lon: Double): CityDbEntity?

}


