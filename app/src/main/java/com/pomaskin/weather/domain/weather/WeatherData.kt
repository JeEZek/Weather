package com.pomaskin.weather.domain.weather

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Immutable
data class WeatherData(
    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int
): Parcelable
