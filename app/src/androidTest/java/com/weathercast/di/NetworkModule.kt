package com.weathercast.di

import android.content.Context
import com.weathercast.BuildConfig
import com.weathercast.helperclasses.NetworkConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val HTTP_TIME_OUT = 15
private const val WEATHER_API_BASE_URL = "http://127.0.0.1:8080"

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
}