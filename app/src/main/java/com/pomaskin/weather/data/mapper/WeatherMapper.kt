package com.pomaskin.weather.data.mapper

import com.pomaskin.weather.data.model.CurrentDataDto
import com.pomaskin.weather.data.model.HourlyDataDto
import com.pomaskin.weather.data.model.WeatherDto
import com.pomaskin.weather.domain.weather.WeatherData
import com.pomaskin.weather.domain.weather.WeatherInfo
import com.pomaskin.weather.domain.weather.WeatherType
import java.time.LocalDateTime

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

private fun CurrentDataDto.toWeatherData(): WeatherData {
    return WeatherData(
        time = LocalDateTime.parse(time),
        weatherType = WeatherType.fromWMO(weatherCode),
        temperature = temperature.toInt(),
        humidity = humidity,
        windSpeed = windSpeed.toInt(),
        pressure = pressure.toInt()
    )
}

private fun HourlyDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time),
                weatherType = WeatherType.fromWMO(weatherCode[index]),
                temperature = temperature[index].toInt(),
                humidity = humidity[index],
                windSpeed = windSpeed[index].toInt(),
                pressure = pressure[index].toInt()
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = hourly.toWeatherDataMap()
    val currentWeatherData = current.toWeatherData()
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}

