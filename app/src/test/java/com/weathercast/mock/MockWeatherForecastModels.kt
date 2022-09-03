package com.weathercast.mock

import com.weathercast.forecast.data.model.*

class MockWeatherForecastModels {
    companion object {

        fun getWeatherForecastResponse(
            cod: String = "200",
            message: String = "some message",
            forecastList: List<Forecast> = listOf(getForecast()),
            city: City = getCity()
        ): WeatherForecastResponse = WeatherForecastResponse(
            cod = cod,
            message = message,
            list = forecastList,
            city = city
        )

        private fun getForecast(
            main: Main = getMain(),
            weather: List<Weather> = listOf(getWeather()),
            dt_txt: String  = "2021-09-10 12:00:00"
        ): Forecast = Forecast(main = main, weather = weather, dt_txt = dt_txt)

        private fun getMain(
            temp: String = "15.43", temp_min: String = "15.36", temp_max: String = "15.43"
        ): Main = Main(temp = temp, temp_min = temp_min, temp_max = temp_max)

        private fun getWeather(
            main: String = "Rain",
            description: String = "Light rain",
            icon: String = "10n"
        ): Weather = Weather(main = main, description = description, icon = icon)

        private fun getCity(
            id: Int = 123, name: String = "someCity", country: String = "someCountry",
            population: Int = 123456789, sunrise: Long = 123456789, sunset: Long = 123456789
        ): City = City(
            id = id, name = name, country = country, population = population,
            sunrise = sunrise, sunset = sunset
        )
    }
}