package com.pomaskin.weather.di

import androidx.lifecycle.ViewModel
import com.pomaskin.weather.presentation.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun bindMainViewModel(viewModel: WeatherViewModel): ViewModel
}