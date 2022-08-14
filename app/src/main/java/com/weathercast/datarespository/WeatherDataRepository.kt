package com.weathercast.datarespository

import com.weathercast.model.response.WeatherForecastResponse
import com.weathercast.util.ApiCallState

interface WeatherDataRepository {
    suspend fun getWeatherForecastByCityName(cityName: String): ApiCallState<WeatherForecastResponse>
}
