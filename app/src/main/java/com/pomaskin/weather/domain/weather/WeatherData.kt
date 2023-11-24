package com.pomaskin.weather.domain.weather

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class WeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Float,
    val humidity: Int,
    val windSpeed: Float,
    val pressure: Float
)
