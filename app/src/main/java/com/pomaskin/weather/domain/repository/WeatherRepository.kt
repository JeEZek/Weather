package com.pomaskin.weather.domain.repository

import com.pomaskin.weather.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(lat: Double, long: Double): Flow<WeatherInfo>
}