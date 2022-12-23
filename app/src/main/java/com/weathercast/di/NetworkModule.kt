package com.weathercast.di

import android.content.Context
import com.weathercast.BuildConfig
import com.weathercast.helperclasses.NetworkConnectivityInterceptor
import com.weathercast.util.DispatcherProvider
import com.weathercast.util.DispatcherProviderImp
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val HTTP_TIME_OUT = 15
private const val WEATHER_API_BASE_URL = "https://api.openweathermap.org/"

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(context: Context): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectivityInterceptor(context))
            .connectTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Reusable
    fun provideRetrofitObject(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Reusable
    fun provideDispatcherProviders(): DispatcherProvider = DispatcherProviderImp()
}