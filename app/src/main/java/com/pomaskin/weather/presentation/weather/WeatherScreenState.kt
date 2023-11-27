package com.pomaskin.weather.presentation.weather

import com.pomaskin.weather.domain.weather.WeatherInfo

sealed class WeatherScreenState {

    object Initial: WeatherScreenState()

    object Loading: WeatherScreenState()

    data class Content(
        val info: WeatherInfo
    ): WeatherScreenState()
}
