package com.pomaskin.weather.domain.usecases

import com.pomaskin.weather.data.repository.WeatherRepositoryImpl
import com.pomaskin.weather.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl
) {

    operator fun invoke(lat: Double, long: Double): Flow<WeatherInfo> {
        return repository.getWeatherData(lat, long)
    }
}
