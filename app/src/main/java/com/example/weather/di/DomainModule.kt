package com.example.weather.di

import com.example.weather.domain.repository.RepositoryWeather
import com.example.weather.domain.useCase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideWeatherUseCase(repositoryWeather: RepositoryWeather): WeatherUseCase =
        WeatherUseCase(repositoryWeather = repositoryWeather)
}

