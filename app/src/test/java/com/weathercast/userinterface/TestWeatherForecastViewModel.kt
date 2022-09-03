package com.weathercast.userinterface

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weathercast.forecast.data.repository.WeatherDataRepository
import com.weathercast.forecast.presentation.WeatherForecastViewModel
import com.weathercast.helperclasses.DataLoadingState
import com.weathercast.mock.MockWeatherForecastModels.Companion.getWeatherForecastResponse
import com.weathercast.mock.RxJava.Companion.setTrampolineSchedulers
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestWeatherForecastViewModel {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var remoteWeatherDataRepository: WeatherDataRepository
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel
    private val dummyCityName: String = "cityName"

    @Before
    fun setup() {
        setTrampolineSchedulers()
        weatherForecastViewModel = WeatherForecastViewModel(remoteWeatherDataRepository)
    }

    @Test
    fun `verify get weather forecast by city for success response`() {
        `when`(remoteWeatherDataRepository.getWeatherForecastByCityName(cityName = dummyCityName))
            .thenReturn(Single.just(getWeatherForecastResponse()))
        weatherForecastViewModel.getWeatherForecast(cityName = dummyCityName)
        assertEquals(
            weatherForecastViewModel.cityNameLiveData.value, dummyCityName
        )
        assertEquals(
            weatherForecastViewModel.weatherForecastLiveData.value, getWeatherForecastResponse()
        )
        assertEquals(
            weatherForecastViewModel.dataLoadingStateLiveData.value, DataLoadingState.Idle
        )
    }

    @Test
    fun `verify get weather forecast by city for error response`() {
        val throwable = Throwable()
        `when`(remoteWeatherDataRepository.getWeatherForecastByCityName(cityName = dummyCityName))
            .thenReturn(Single.error(throwable))
        weatherForecastViewModel.getWeatherForecast(cityName = dummyCityName)
        assertEquals(
            weatherForecastViewModel.cityNameLiveData.value, dummyCityName
        )
        assertEquals(
            weatherForecastViewModel.weatherForecastLiveData.value, null
        )
        val liveDataError = weatherForecastViewModel.dataLoadingStateLiveData.value as? DataLoadingState.Error
        assertEquals(liveDataError?.throwable, throwable)
    }
}