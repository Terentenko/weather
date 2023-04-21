package com.example.weather.di

import com.example.weather.data.repository.RepositoryDataBase
import com.example.weather.data.repository.RepositoryDataBaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseModule {
    @Binds
    @Singleton
    abstract fun bindRepositoryDataBase(RepositoryDataBase: RepositoryDataBaseImpl): RepositoryDataBase

}