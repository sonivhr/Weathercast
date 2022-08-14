package com.weathercast.domain

import com.weathercast.datarespository.WeatherDataRepository
import com.weathercast.model.response.WeatherForecastResponse
import com.weathercast.util.ApiCallState
import javax.inject.Inject

class CityWeatherForecastUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(cityName: String): ApiCallState<WeatherForecastResponse> =
        weatherDataRepository.getWeatherForecastByCityName(cityName = cityName)
}
