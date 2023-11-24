package com.pomaskin.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherDto (
    @SerializedName("hourly") val weatherData: WeatherDataDto
)