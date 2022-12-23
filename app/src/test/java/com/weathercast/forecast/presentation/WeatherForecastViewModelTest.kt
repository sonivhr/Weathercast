package com.weathercast.forecast.presentation


import app.cash.turbine.test
import com.weathercast.forecast.data.model.WeatherForecastResponse
import com.weathercast.forecast.domain.CityWeatherForecastUseCase
import com.weathercast.util.ApiCallState
import com.weathercast.util.DispatcherProvider
import com.weathercast.util.DispatcherProviderImp
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherForecastViewModelTest {
    private val cityWeatherForecastUseCase: CityWeatherForecastUseCase = mockk()
    private val dispatcherProvider: DispatcherProvider = DispatcherProviderImp()
    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

    @Before
    fun setUp() {
        weatherForecastViewModel = WeatherForecastViewModel(
            cityWeatherForecastUseCase,
            dispatcherProvider
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify city name being emitted`() = runTest {
        val mockWeatherForecastResponse: ApiCallState<WeatherForecastResponse> = mockk()
        coEvery {
            cityWeatherForecastUseCase.invoke(ofType(String::class))
        } returns mockWeatherForecastResponse

        weatherForecastViewModel.cityNameState.test {
            assertEquals(DEFAULT_CITY_NAME, awaitItem())

            weatherForecastViewModel.getWeatherForecast(cityName = CITY_NAME)

            assertEquals(CITY_NAME, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify cityWeatherForecastState when cityWeatherForecast API succeeds`() = runTest {
        val mockWeatherForecastResponse: ApiCallState<WeatherForecastResponse> = mockk()
        coEvery {
            cityWeatherForecastUseCase.invoke(ofType(String::class))
        } returns mockWeatherForecastResponse

        weatherForecastViewModel.cityWeatherForecastState.test {
            assert(awaitItem() is ApiCallState.Loading)

            weatherForecastViewModel.getWeatherForecast(cityName = CITY_NAME)

            assertEquals(mockWeatherForecastResponse, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify cityWeatherForecastState when cityWeatherForecast API fails`() = runTest {
        val mockWeatherForecastResponse = ApiCallState.Error(exception = Exception("Test Exception Message"))
        coEvery {
            cityWeatherForecastUseCase.invoke(ofType(String::class))
        } returns mockWeatherForecastResponse

        weatherForecastViewModel.cityWeatherForecastState.test {
            assert(awaitItem() is ApiCallState.Loading)

            weatherForecastViewModel.getWeatherForecast(cityName = CITY_NAME)

            assertEquals(awaitItem(), mockWeatherForecastResponse)
        }
    }

    private companion object {
        const val DEFAULT_CITY_NAME = "London"
        const val CITY_NAME = "any city"
    }
}
