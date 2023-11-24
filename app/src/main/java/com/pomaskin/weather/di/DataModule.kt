package com.pomaskin.weather.di

import com.pomaskin.weather.data.network.ApiFactory
import com.pomaskin.weather.data.network.ApiService
import com.pomaskin.weather.data.repository.WeatherRepositoryImpl
import com.pomaskin.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}