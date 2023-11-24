package com.pomaskin.weather.presentation.weather

import androidx.lifecycle.ViewModel
import com.pomaskin.weather.domain.usecases.GetWeatherDataUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
): ViewModel() {
    val state = getWeatherDataUseCase(lat = 31.5, long = 34.75)
        .map { WeatherScreenState.Content(it) as WeatherScreenState }
        .onStart { WeatherScreenState.Initial }
}