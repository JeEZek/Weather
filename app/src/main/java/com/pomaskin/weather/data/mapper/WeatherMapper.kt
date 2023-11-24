package com.pomaskin.weather.data.mapper

import com.pomaskin.weather.data.model.WeatherDataDto
import com.pomaskin.weather.data.model.WeatherDto
import com.pomaskin.weather.domain.weather.WeatherData
import com.pomaskin.weather.domain.weather.WeatherInfo
import com.pomaskin.weather.domain.weather.WeatherType
import java.time.LocalDateTime
import javax.inject.Inject

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

private fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time),
                weatherType = WeatherType.fromWMO(weatherCode[index]),
                temperature = temperature[index],
                humidity = humidity[index],
                windSpeed = windSpeed[index],
                pressure = pressure[index]
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}

