package com.weathercast.apiinterface

import com.weathercast.forecast.data.model.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "ee76fbb294ea96f735ba6c1276ca5d7c"
interface OpenWeatherMapApiInterface {
    @GET("data/2.5/forecast?")
    suspend fun getWeatherForecastByCityName(
        @Query("q", encoded = true) cityName: String = "London",
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = "metric"
    ) : Response<WeatherForecastResponse>
}
