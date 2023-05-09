package com.example.weather.di


import com.example.weather.data.repository.RepositoryDataBase
import com.example.weather.data.repository.RepositoryNetwork
import com.example.weather.data.repository.RepositoryNetworkImpl
import com.example.weather.data.repository.RepositorySharedPref
import com.example.weather.data.repository.RepositoryWeatherImpl
import com.example.weather.domain.repository.RepositoryWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepositoryNetwork(): RepositoryNetwork = RepositoryNetworkImpl()

    @Provides
    @Singleton
    fun provideRepositoryWeather(
        repositoryDataBase: RepositoryDataBase ,
        repositoryNetwork: RepositoryNetwork,
        repositorySharedPref: RepositorySharedPref
    ): RepositoryWeather {
        return RepositoryWeatherImpl(
            repositoryDataBase =repositoryDataBase ,
            repositoryNetwork =repositoryNetwork ,
            repositorySharedPref =repositorySharedPref

        )
    }

}

