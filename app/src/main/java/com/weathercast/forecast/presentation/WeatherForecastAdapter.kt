package com.weathercast.forecast.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weathercast.R
import com.weathercast.forecast.data.model.Forecast
import com.weathercast.util.convertToReadableDateTime
import com.weathercast.util.loadOriginalImageWithGlide

class WeatherForecastAdapter(
    private val weatherForecastList: MutableList<Forecast>
) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_weather_forecast, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val forecastRow = weatherForecastList[position]
        viewHolder.tvTime.text = forecastRow.dt_txt.convertToReadableDateTime()
        val main = forecastRow.main
        viewHolder.tvTemperature.text = viewHolder.tvTime.context.getString(
            R.string.temperature_text, main.temp_max, main.temp_min
        )
        viewHolder.ivWeatherIcon.loadOriginalImageWithGlide(forecastRow.weather[0].icon)
        viewHolder.tvWeatherDescription.text = forecastRow.weather[0].description.capitalize()
    }

    override fun getItemCount(): Int = weatherForecastList.size

    fun updateWeatherForecast(newWeatherForecastList: List<Forecast>) {
        weatherForecastList.clear()
        weatherForecastList.addAll(newWeatherForecastList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTemperature: TextView = view.findViewById(R.id.tvTemperature)
        val ivWeatherIcon: ImageView = view.findViewById(R.id.ivWeatherIcon)
        val tvWeatherDescription: TextView = view.findViewById(R.id.tvWeatherDescription)
    }
}