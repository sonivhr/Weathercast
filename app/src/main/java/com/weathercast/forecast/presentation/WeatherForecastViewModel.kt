package com.weathercast.forecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathercast.forecast.domain.CityWeatherForecastUseCase
import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.util.ApiCallState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_CITY_NAME = "London"

class WeatherForecastViewModel @Inject constructor(
    private val cityWeatherForecastUseCase: CityWeatherForecastUseCase
) : ViewModel() {
    private val _cityWeatherForecastState: MutableStateFlow<ApiCallState<WeatherForecastResponse>> =
        MutableStateFlow(ApiCallState.Loading)
    val cityWeatherForecastState = _cityWeatherForecastState.asStateFlow()

    private val _cityNameState: MutableStateFlow<String> = MutableStateFlow(DEFAULT_CITY_NAME)
    val cityNameState = _cityNameState.asStateFlow()

    fun getWeatherForecast(cityName: String = "London") {
        viewModelScope.launch {
            _cityNameState.value = cityName
            _cityWeatherForecastState.value = cityWeatherForecastUseCase(cityName = cityName)
        }
    }
}
