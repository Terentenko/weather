package com.example.weather.data.dataBase.dao

import androidx.room.*
import com.example.weather.data.dataBase.entity.CurrentWeatherDbEntity

@Dao
interface CurrentWeatherDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCurrentWeather(currentWeatherDbEntity: CurrentWeatherDbEntity)


    @Query("SELECT * FROM weather WHERE city_name =:cityName")
    suspend fun getListCurrentWeather(cityName: String): List<CurrentWeatherDbEntity>?

    @Query("DELETE FROM weather")
    fun deleteAll()

}