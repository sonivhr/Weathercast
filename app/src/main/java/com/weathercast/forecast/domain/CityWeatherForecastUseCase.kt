package com.weathercast.forecast.domain

import com.weathercast.forecast.data.repository.WeatherDataRepository
import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.util.ApiCallState
import javax.inject.Inject

class CityWeatherForecastUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(cityName: String): ApiCallState<WeatherForecastResponse> =
        weatherDataRepository.getWeatherForecastByCityName(cityName = cityName)
}
