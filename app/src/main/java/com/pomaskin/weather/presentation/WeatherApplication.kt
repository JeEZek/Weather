package com.pomaskin.weather.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pomaskin.weather.di.ApplicationComponent
import com.pomaskin.weather.di.DaggerApplicationComponent

class WeatherApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as WeatherApplication).component
}