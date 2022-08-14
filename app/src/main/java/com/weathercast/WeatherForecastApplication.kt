package com.weathercast

import android.app.Application
import com.weathercast.di.ApplicationComponent
import com.weathercast.di.DaggerApplicationComponent

class WeatherForecastApplication: Application() {
    private var appComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        getAppComponent()
    }

    fun getAppComponent(): ApplicationComponent {
        if (appComponent == null) {
            appComponent = DaggerApplicationComponent.factory().create(this)
        }
        return requireNotNull(appComponent)
    }
}