package com.example.android_base.retrofit

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val type: Int? = null
) {
    companion object {
        fun <T> success(data: T, type: Int?): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, type = type)

        fun <T> error(data: T?, message: String? = "Error", type: Int? = null): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message, type = type)

        fun <T> loading(data: T?, type: Int? = null): Resource<T> =
            Resource(status = Status.LOADING, data = data, type = type)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
}