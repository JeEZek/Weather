package com.pomaskin.weather.presentation.weather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pomaskin.weather.presentation.getApplicationComponent
import com.pomaskin.weather.ui.theme.WeatherTheme

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                val component = getApplicationComponent()
                val viewModel: WeatherViewModel = viewModel(factory = component.getViewModelFactory())
                val screenState= viewModel.state.collectAsState(WeatherScreenState.Initial)

                when(val currentState = screenState.value) {
                    is WeatherScreenState.Content -> {
                        Log.d("MainActivity", "${currentState.info}")
                    }
                    is WeatherScreenState.Initial -> {}
                }
            }
        }
    }
}