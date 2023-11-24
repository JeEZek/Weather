package com.pomaskin.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherDataDto (
    @SerializedName("time") val time: List<String>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("temperature_2m") val temperature: List<Float>,
    @SerializedName("relative_humidity_2m") val humidity: List<Int>,
    @SerializedName("wind_speed_10m") val windSpeed: List<Float>,
    @SerializedName("pressure_msl") val pressure: List<Float>,
)