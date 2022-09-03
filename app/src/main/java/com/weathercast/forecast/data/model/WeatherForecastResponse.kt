package com.weathercast.forecast.data.model

data class WeatherForecastResponse(
    val cod: String,
    val message: String,
    val list: List<Forecast>,
    val city: City
)

data class Forecast(
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
)

data class Main(
    val temp: String,
    val temp_min: String,
    val temp_max: String
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val population: Int,
    val sunrise: Long,
    val sunset: Long
)
