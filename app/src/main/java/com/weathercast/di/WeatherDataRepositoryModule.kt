package com.weathercast.di

import com.weathercast.apiinterface.OpenWeatherMapApiInterface
import com.weathercast.datarespository.WeatherDataRepositoryImpl
import com.weathercast.datarespository.WeatherDataRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class WeatherDataRepositoryModule {
    @Provides
    @Reusable
    fun provideRemoteWeatherDataRepository(
        openWeatherMapApiInterface: OpenWeatherMapApiInterface
    ): WeatherDataRepository = WeatherDataRepositoryImpl(openWeatherMapApiInterface)
}
