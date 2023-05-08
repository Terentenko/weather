package com.example.weather.data.dataBase.entity

import androidx.room.*
import com.example.weather.domain.model.CurrentWeather


@Entity(
    tableName = "weather",
    indices = [
        Index("date"),
        Index("city_name")
    ],
    foreignKeys = [
        ForeignKey(
            entity = CityDbEntity::class,
            parentColumns = ["name"],
            childColumns = ["city_name"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE

        )
    ]
)
data class CurrentWeatherDbEntity(
    @PrimaryKey(autoGenerate = true)
    val auto_id: Int ,

    val date: Long,
    val city_name: String,

    val sunrise: Long,
    val sunset: Long,
    val win_deg: Int,
    val win_gust: Double,
    val win_speed: Double,
    val clouds: String,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val temp_max: Double,
    val temp_min: Double,
    val sea_level: Int,
    val grnd_level: Int


) {
    fun toCurrentWeather(city: CityDbEntity) = CurrentWeather(

        date = date,
        city = city.toCity(),
        sunrise = sunrise,
        sunset = sunset,
        winDeg = win_deg,
        winGust = win_gust,
        winSpeed = win_speed,
        clouds = clouds,
        temp = temp,
        feelsLike = feels_like,
        pressure = pressure,
        humidity = humidity,
        tempMax = temp_max,
        tempMin = temp_min,
        seaLevel = sea_level,
        grndLevel = grnd_level

    )

    companion object {
        fun fromCurrentWeather(currentWeather: CurrentWeather) = CurrentWeatherDbEntity(
            auto_id = 0,
            date = currentWeather.date,
            city_name = currentWeather.city.name,
            sunrise = currentWeather.sunrise,
            sunset = currentWeather.sunset,
            win_deg = currentWeather.winDeg,
            win_gust = currentWeather.winGust,
            win_speed = currentWeather.winSpeed,
            clouds = currentWeather.clouds,
            temp = currentWeather.temp,
            feels_like = currentWeather.feelsLike,
            pressure = currentWeather.pressure,
            humidity = currentWeather.humidity,
            temp_max = currentWeather.tempMax,
            temp_min = currentWeather.tempMin,
            sea_level = currentWeather.seaLevel,
            grnd_level = currentWeather.grndLevel

        )
    }
}
