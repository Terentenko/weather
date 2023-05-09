package com.example.weather.di

import com.example.weather.data.okHttp.OkHttpSource
import com.example.weather.data.repository.RepositoryNetwork
import com.example.weather.data.repository.RepositoryNetworkImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRepositoryNetwork(okHttpSource: OkHttpSource): RepositoryNetwork =
        RepositoryNetworkImpl(okHttpSource = okHttpSource)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY) // be for log work okHTTp
            ).build()

}