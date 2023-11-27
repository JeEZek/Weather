package com.pomaskin.weather.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pomaskin.weather.R
import com.pomaskin.weather.domain.weather.WeatherData
import com.pomaskin.weather.domain.weather.WeatherType
import com.pomaskin.weather.presentation.getApplicationComponent
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Weather() {
    val component = getApplicationComponent()
    val viewModel: WeatherViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.state.collectAsState(WeatherScreenState.Initial)

    WeatherContent(screenState)
}

@Composable
private fun WeatherContent(
    screenState: State<WeatherScreenState>
) {
    when (val currentState = screenState.value) {
        is WeatherScreenState.Content -> {
            val daysOfWeek = currentState.info.weatherDataPerDay.values
                .flatMap { it.take(1) }
                .map { it.time.dayOfWeek }

            val minTemp = currentState.info.weatherDataPerDay.values.map {
                it.minOf { it.temperature }
            }

            val maxTemp = currentState.info.weatherDataPerDay.values.map {
                it.maxOf { it.temperature }
            }

            WeatherScreenStateIsContent(
                currentState,
                daysOfWeek,
                minTemp,
                maxTemp
            )
        }

        is WeatherScreenState.Loading -> {

            WeatherScreenStateIsLoading()
        }

        is WeatherScreenState.Initial -> {}
    }
}

@Composable
private fun WeatherScreenStateIsContent(
    currentState: WeatherScreenState.Content,
    daysOfWeek: List<DayOfWeek>,
    minTemp: List<Int>,
    maxTemp: List<Int>
) {
    var weatherState by rememberWeatherState(
        weatherInfo = currentState.info
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        WeatherCard(
            time = weatherState.showingWeather.time,
            weatherType = weatherState.showingWeather.weatherType,
            temperature = weatherState.showingWeather.temperature,
            humidity = weatherState.showingWeather.humidity,
            windSpeed = weatherState.showingWeather.windSpeed,
            pressure = weatherState.showingWeather.pressure,
            dayName = weatherState.showingWeather.time.dayOfWeek
        )

        WeatherForecast(
            dailyWeather = weatherState.dailyWeather,
            hourState = weatherState.hourState,
            dayState = weatherState.dayState,
            nowHour = currentState.info.currentWeatherData.time.hour,
            daysOfWeek = daysOfWeek,
            minTemp = minTemp,
            maxTemp = maxTemp,
            onHourStateChanged = {
                weatherState = weatherState.copy(hourState = it)
            },
            onDayStateChanged = {
                weatherState = weatherState.copy(dayState = it)
            },
            onNowStateChanged = {
                weatherState = weatherState.copy(isNowState = it)
            }
        )
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier = Modifier,
    time: LocalDateTime,
    weatherType: WeatherType,
    temperature: Int,
    humidity: Int,
    windSpeed: Int,
    pressure: Int,
    dayName: DayOfWeek
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$dayName ${
                    time.format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                }"
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            AsyncImage(
                modifier = Modifier
                    .size(200.dp),
                model = weatherType.iconRes,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "${temperature}°C",
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = weatherType.weatherDesc
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconWithTextRow(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                    text = "${pressure}hpa",
                )
                IconWithTextRow(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop),
                    text = "${humidity}%",
                )
                IconWithTextRow(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    text = "${windSpeed}km/h",
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherForecast(
    dailyWeather: List<WeatherData>,
    hourState: Int,
    dayState: Int,
    nowHour: Int,
    daysOfWeek: List<DayOfWeek>,
    minTemp: List<Int>,
    maxTemp: List<Int>,
    onHourStateChanged: (Int) -> Unit,
    onDayStateChanged: (Int) -> Unit,
    onNowStateChanged: (Boolean) -> Unit,
) {
    //Hour
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(dailyWeather.size) {
            Card(
                modifier = Modifier.padding(4.dp),
                colors = if (dailyWeather[it].time.hour == hourState) {
                    CardDefaults.cardColors(
                        containerColor = colorScheme.secondaryContainer,
                        contentColor = colorScheme.onSecondaryContainer
                    )
                } else {
                    CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceVariant,
                        contentColor = colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    onHourStateChanged(
                        dailyWeather[it].time.hour
                    )
                    onNowStateChanged(
                        dayState == 0 && dailyWeather[it].time.hour == dailyWeather[0].time.hour
                    )
                }
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (dayState == 0 && it == 0) {
                        Text(
                            text = "Now"
                        )
                    } else {
                        Text(
                            text = dailyWeather[it].time.format(
                                DateTimeFormatter.ofPattern("HH")
                            )
                        )
                    }
                    AsyncImage(
                        modifier = Modifier
                            .size(30.dp),
                        model = dailyWeather[it].weatherType.iconRes,
                        contentDescription = null
                    )
                    Text(
                        text = "${dailyWeather[it].temperature}°C"
                    )
                }
            }
        }
    }
    //Day
    Column {
        repeat(7) {
            Card(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                colors = if (it == dayState) {
                    CardDefaults.cardColors(
                        containerColor = colorScheme.secondaryContainer,
                        contentColor = colorScheme.onSecondaryContainer
                    )
                } else {
                    CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceVariant,
                        contentColor = colorScheme.onSurfaceVariant
                    )
                },
                onClick = {
                    if (it == 0 && hourState < nowHour) {
                        onHourStateChanged(nowHour)
                    }
                    onDayStateChanged(it)
                    onNowStateChanged(
                        hourState == 0 && dayState == 0
                    )
                }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = if (it == 0) daysOfWeek[it].name + " (today)" else daysOfWeek[it].name
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ){
                        Text(
                            text = "${minTemp[it]}°C - ${maxTemp[it]}°C"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherScreenStateIsLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorScheme.primary
        )
    }
}

@Composable
fun IconWithTextRow(
    imageVector: ImageVector,
    modifierIcon: Modifier = Modifier.size(25.dp),
    text: String,
    modifierText: Modifier = Modifier,
    contentDescription: String? = null
) {
    Row {
        Icon(
            modifier = modifierIcon,
            imageVector = imageVector,
            contentDescription = contentDescription
        )
        Text(
            modifier = modifierText,
            text = text
        )
    }
}