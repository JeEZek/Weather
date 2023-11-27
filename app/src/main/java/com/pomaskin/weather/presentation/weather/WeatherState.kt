package com.pomaskin.weather.presentation.weather

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.pomaskin.weather.domain.weather.WeatherData
import com.pomaskin.weather.domain.weather.WeatherInfo
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek

@Parcelize
@Immutable
data class WeatherState(
    val weatherInfo: WeatherInfo,
    @IgnoredOnParcel val isNowState: Boolean = true,
    @IgnoredOnParcel val hourState: Int = weatherInfo.currentWeatherData.time.hour,
    @IgnoredOnParcel val dayState: Int = 0,
): Parcelable {

    @IgnoredOnParcel private val dailyWeatherDataList = weatherInfo.weatherDataPerDay
    @IgnoredOnParcel private val currentWeatherData = weatherInfo.currentWeatherData

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

    //TODO change error
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
): MutableState<WeatherState> = rememberSaveable {
    mutableStateOf(
        WeatherState(
            weatherInfo
        )
    )
}