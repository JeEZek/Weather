package com.pomaskin.weather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentDataDto(
    @SerializedName("time") val time: String,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("temperature_2m") val temperature: Float,
    @SerializedName("relative_humidity_2m") val humidity: Int,
    @SerializedName("wind_speed_10m") val windSpeed: Float,
    @SerializedName("pressure_msl") val pressure: Float,
)
