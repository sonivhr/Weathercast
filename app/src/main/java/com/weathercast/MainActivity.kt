package com.weathercast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weathercast.forecast.presentation.WeatherForecastFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, WeatherForecastFragment())
            .commit()
    }
}
