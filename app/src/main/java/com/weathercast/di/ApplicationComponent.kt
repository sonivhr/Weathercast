package com.weathercast.di

import android.content.Context
import com.weathercast.forecast.presentation.WeatherForecastFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        WeatherDataRepositoryModule::class,
        RetrofitApiInterfaceModule::class
    ]
)
interface ApplicationComponent {

    fun inject(weatherForecastFragment: WeatherForecastFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }
}