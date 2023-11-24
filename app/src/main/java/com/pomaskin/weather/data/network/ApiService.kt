package com.pomaskin.weather.data.network

import com.pomaskin.weather.data.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast?hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m,pressure_msl")
    suspend fun loadWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}