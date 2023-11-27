package com.pomaskin.weather.domain.weather

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class WeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int
)
