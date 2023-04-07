package com.example.weather.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.dataBase.dao.*
import com.example.weather.data.dataBase.entity.*



@Database(
    version = 1,
    entities = [
        CityDbEntity::class,
        CurrentWeatherDbEntity::class
    ]
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun getCitiDao(): CityDao
    abstract fun getCurrentWeatherDao(): CurrentWeatherDao

    companion object {
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "database.db"
                ).build().also { instance = it }
            }
        }
    }


}