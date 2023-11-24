package com.pomaskin.weather.presentation.weather

import com.pomaskin.weather.domain.weather.WeatherInfo

sealed class WeatherScreenState {

    object Initial: WeatherScreenState()

    data class Content(val info: WeatherInfo): WeatherScreenState()
}
