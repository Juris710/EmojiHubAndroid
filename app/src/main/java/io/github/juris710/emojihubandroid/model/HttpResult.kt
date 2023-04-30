package io.github.juris710.emojihubandroid.model

sealed class HttpResult<T> {
    class Success<T>(val data: T) : HttpResult<T>()
    class Error<T>(val message: UiText) : HttpResult<T>()
    class Loading<T> : HttpResult<T>()
}
