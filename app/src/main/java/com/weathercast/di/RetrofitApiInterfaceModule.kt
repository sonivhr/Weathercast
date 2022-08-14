package com.weathercast.di

import com.weathercast.apiinterface.OpenWeatherMapApiInterface
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class RetrofitApiInterfaceModule {
    @Provides
    @Reusable
    fun provideWeatherApiClient(retrofit: Retrofit): OpenWeatherMapApiInterface =
        retrofit.create(OpenWeatherMapApiInterface::class.java)
}
