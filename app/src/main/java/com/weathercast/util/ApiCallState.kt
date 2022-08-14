package com.weathercast.util

import com.google.gson.Gson
import com.weathercast.helperclasses.NoInternetException
import com.weathercast.model.response.RetrofitErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

private const val UNKNOWN_ERROR_MESSAGE = "Unknown error message"
private val gson = Gson()

sealed class ApiCallState<out T : Any> {
    object Loading : ApiCallState<Nothing>()

    data class Success<out T : Any>(val data: T) : ApiCallState<T>()

    data class Error(val exception: Exception) : ApiCallState<Nothing>()
}

suspend fun <T : Any> webApiCall(
    call: suspend () -> Response<T>,
    dispatchers: CoroutineDispatcher = Dispatchers.IO
): ApiCallState<T> = try {
    withContext(dispatchers) {
        val apiResponse = call.invoke()
        if (apiResponse.isSuccessful) {
            ApiCallState.Success(apiResponse.body()!!)
        } else {
            apiResponse.errorBody()?.run {
                val retrofitErrorResponse =
                    gson.fromJson(charStream(), RetrofitErrorResponse::class.java)
                return@withContext ApiCallState.Error(IOException(retrofitErrorResponse.message))
            } ?: ApiCallState.Error(IOException())
        }
    }
} catch (e: Exception) {
    when (e) {
        is NoInternetException -> ApiCallState.Error(e)
        else -> ApiCallState.Error(e)
    }
}
