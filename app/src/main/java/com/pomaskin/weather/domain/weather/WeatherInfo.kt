package com.pomaskin.weather.domain.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData
): Parcelable
