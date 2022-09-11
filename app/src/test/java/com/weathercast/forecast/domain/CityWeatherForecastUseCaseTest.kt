package com.weathercast.forecast.domain

import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.forecast.data.repository.WeatherDataRepository
import com.weathercast.helperclasses.NoInternetException
import com.weathercast.util.ApiCallState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CityWeatherForecastUseCaseTest {
    private val weatherDataRepository: WeatherDataRepository = mockk()
    private val weatherForecastResponse: WeatherForecastResponse = mockk()

    private lateinit var cityWeatherForecastUseCase: CityWeatherForecastUseCase

    @Before
    fun setup() {
        cityWeatherForecastUseCase = CityWeatherForecastUseCase(weatherDataRepository)
    }

    @Test
    fun `verify invoke when getWeatherForecastByCityName api succeeds`() = runTest {
        coEvery {
            weatherDataRepository.getWeatherForecastByCityName(ofType(String::class))
        } returns ApiCallState.Success(weatherForecastResponse)

        assertEquals(
            ApiCallState.Success(weatherForecastResponse),
            weatherDataRepository.getWeatherForecastByCityName(CITY_NAME)
        )
    }

    @Test
    fun `verify invoke when getWeatherForecastByCityName api fails`() = runTest {
        val apiException = Exception("weather forecast api error")
        coEvery {
            weatherDataRepository.getWeatherForecastByCityName(ofType(String::class))
        } returns ApiCallState.Error(exception = apiException)

        assertEquals(
            ApiCallState.Error(exception = apiException),
            weatherDataRepository.getWeatherForecastByCityName(CITY_NAME)
        )
    }

    @Test
    fun `verify invoke when getWeatherForecastByCityName api returns connectivity issue`() = runTest {
        val noConnectivityException = NoInternetException()
        coEvery {
            weatherDataRepository.getWeatherForecastByCityName(ofType(String::class))
        } returns ApiCallState.Error(exception = noConnectivityException)

        assertEquals(
            ApiCallState.Error(exception = noConnectivityException),
            weatherDataRepository.getWeatherForecastByCityName(CITY_NAME)
        )
    }

    companion object {
        private const val CITY_NAME = "some city name"
    }
}
