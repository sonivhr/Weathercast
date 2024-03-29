package com.weathercast.forecast.data.repository

import com.weathercast.apiinterface.OpenWeatherMapApiInterface
import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.util.ApiCallState
import com.weathercast.util.webApiCall

class WeatherDataRepositoryImpl(
    private val weatherRemoteData: OpenWeatherMapApiInterface
): WeatherDataRepository {
    override suspend fun getWeatherForecastByCityName(cityName: String): ApiCallState<WeatherForecastResponse> {
        return webApiCall(
            call = {
                weatherRemoteData.getWeatherForecastByCityName(cityName = cityName)
            }
        )
    }
}
