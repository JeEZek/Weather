package com.pomaskin.weather.data.repository

import com.pomaskin.weather.data.mapper.toWeatherInfo
import com.pomaskin.weather.data.network.ApiService
import com.pomaskin.weather.domain.repository.WeatherRepository
import com.pomaskin.weather.domain.weather.WeatherInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): WeatherRepository {
    override fun getWeatherData(lat: Double, long: Double): Flow<WeatherInfo> = flow {
        val weatherInfo = apiService.loadWeather(
            lat = lat,
            long = long
        ).toWeatherInfo()
        emit(weatherInfo)
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    //TODO if error

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}
