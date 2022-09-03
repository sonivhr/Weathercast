package com.weathercast.forecast.data.repository

import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.util.ApiCallState

interface WeatherDataRepository {
    suspend fun getWeatherForecastByCityName(cityName: String): ApiCallState<WeatherForecastResponse>
}
