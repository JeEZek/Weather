package com.pomaskin.weather.data.network

import com.pomaskin.weather.data.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast" +
            "?current=temperature_2m,relative_humidity_2m,weather_code,pressure_msl,wind_speed_10m" +
            "&hourly=temperature_2m,relative_humidity_2m,weather_code,pressure_msl,wind_speed_10m" +
            "&timezone=auto"
    )
    suspend fun loadWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}