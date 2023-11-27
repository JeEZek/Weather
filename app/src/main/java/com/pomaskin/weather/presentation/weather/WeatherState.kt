package com.pomaskin.weather.presentation.weather

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pomaskin.weather.domain.weather.WeatherData
import com.pomaskin.weather.domain.weather.WeatherInfo
import java.time.DayOfWeek

//TODO rememberSavable
//TODO change errors
@Immutable
data class WeatherState(
    val weatherInfo: WeatherInfo,
    val isNowState: Boolean = true,
    val hourState: Int = weatherInfo.currentWeatherData.time.hour,
    val dayState: Int = 0,
) {
    private val dailyWeatherDataList = weatherInfo.weatherDataPerDay
    private val currentWeatherData = weatherInfo.currentWeatherData

    val dailyWeather: List<WeatherData>
        get() {
            Log.d("TestDailyWeather", "weatherInfo.currentWeatherData.time.dayOfMonth")
            return if (dayState == 0) {
                listOf(currentWeatherData) + (dailyWeatherDataList[0]?.filter {
                    it.time.hour > currentWeatherData.time.hour
                } ?: throw Exception(""))
            } else {
                dailyWeatherDataList[dayState] ?: throw Exception("")
            }
        }

    val showingWeather: WeatherData
        get() {
            return dailyWeather.find {
                it.time.hour == hourState
            } ?: throw Error("")
        }
}

@Composable
fun rememberWeatherState(
    weatherInfo: WeatherInfo
): MutableState<WeatherState> = remember {
    mutableStateOf(
        WeatherState(
            weatherInfo
        )
    )
}