package com.pomaskin.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherDto (
    @SerializedName("current") val current: CurrentDataDto,
    @SerializedName("hourly") val hourly: HourlyDataDto
)