package com.weathercast.helperclasses

import android.content.Context
import com.weathercast.util.isInternetConnected
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectivityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isInternetConnected()) {
            throw NoInternetException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}