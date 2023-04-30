package io.github.juris710.emojihubandroid.data

sealed class HttpResult<T>{
    class Success<T>(val data: T): HttpResult<T>()
    class Error<T>(val message: String): HttpResult<T>()
    class Loading<T>: HttpResult<T>()
}
