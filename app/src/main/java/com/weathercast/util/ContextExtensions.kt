package com.weathercast.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weathercast.WeatherForecastApplication
import com.weathercast.di.ApplicationComponent
import com.weathercast.helperclasses.BaseViewModelFactory
import dagger.Lazy

fun Fragment.app(): WeatherForecastApplication =
    context?.applicationContext as WeatherForecastApplication

fun Fragment.appComponent(): ApplicationComponent = app().getAppComponent()

fun Context.isInternetConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

inline fun <reified T : ViewModel> Fragment.getFragmentViewModel(creator: Lazy<T>? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this,
            BaseViewModelFactory { creator.get() }).get(T::class.java)
}
