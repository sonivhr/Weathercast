package com.weathercast.di

import com.weathercast.apiinterface.OpenWeatherMapApiInterface
import com.weathercast.forecast.data.repository.WeatherDataRepositoryImpl
import com.weathercast.forecast.data.repository.WeatherDataRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class WeatherDataRepositoryModule {
    @Provides
    @Reusable
    fun provideRemoteWeatherDataRepository(
        openWeatherMapApiInterface: OpenWeatherMapApiInterface
    ): WeatherDataRepository = WeatherDataRepositoryImpl(openWeatherMapApiInterface)
}
