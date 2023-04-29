package io.github.juris710.emojihubandroid

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

suspend fun <T> handleHttpRequest(exec: suspend () -> Response<T>): T? {
    val response = try {
        exec()
    } catch (e: Exception) {
        Timber.e(e)
        val errorMessage = when (e) {
            is IOException -> "You might not have Internet connection."
            is HttpException -> "Request Failed with status code ${e.code()}"
            else -> "Unknown Error"
        }
        throw Exception(errorMessage)
    }
    val body = response.body()
    if (!response.isSuccessful) {
        throw Exception("Request failed with status code ${response.code()}")
    }
    return body
}