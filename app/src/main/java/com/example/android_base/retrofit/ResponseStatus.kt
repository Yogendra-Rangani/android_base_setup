package com.example.android_base.retrofit

sealed class ResponseStatus<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResponseStatus<T>(data = data)
    class Error<T>(errorMessage: String ) : ResponseStatus<T>(message = errorMessage)
    class Alert<T>(alert: String ) : ResponseStatus<T>(message = alert)
    class Loading<T> : ResponseStatus<T>()
}
