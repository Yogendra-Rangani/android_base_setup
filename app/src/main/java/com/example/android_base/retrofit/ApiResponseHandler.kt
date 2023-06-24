package com.example.android_base.retrofit

import com.example.android_base.constants.Constant.Companion.INTERNAL_SERVER_ERROR
import com.example.android_base.constants.Constant.Companion.NETWORK_CONNECTION
import com.example.android_base.constants.Constant.Companion.SERVER_BUSY_ERROR
import com.example.android_base.constants.Constant.Companion.SOMETHING_WRONG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

abstract class ApiResponseHandler {
    private var mErrorMsg: String = ""

    suspend fun <T> safeApiCall(
        apiToBeCalled: suspend () -> Response<T>,
    ): ResponseStatus<T> {
        return withContext(Dispatchers.IO) {
//            if (!isNetworkAvailable) {
//                return@withContext ResponseStatus.Error(NETWORK_CONNECTION)
//            }

            val response: Response<T> = apiToBeCalled()
            try {
                if (response.isSuccessful && response.body() != null) {
                    ResponseStatus.Success(data = response.body()!!)
                } else {
                    when (response.code()) {
                        401 -> {
                            mErrorMsg = decodeError(response.errorBody())!!
                            ResponseStatus.Alert<T>(alert = mErrorMsg)
                        }
                        502 -> {
                            mErrorMsg = decodeError(response.errorBody())!!
                            ResponseStatus.Error<T>(errorMessage = SERVER_BUSY_ERROR)
                        }
                        500 -> {
                            mErrorMsg = decodeError(response.errorBody())!!
                            ResponseStatus.Error<T>(errorMessage = INTERNAL_SERVER_ERROR)
                        }
                        else -> {
                            mErrorMsg = decodeError(response.errorBody())!!
                            ResponseStatus.Error(errorMessage = mErrorMsg)
                        }
                    }
                }
            } catch (throwable: Throwable) {
                when (throwable) {

                    is IOException -> {
                        ResponseStatus.Error(NETWORK_CONNECTION)
                    }
                    is HttpException -> {
                        ResponseStatus.Error(
                            errorMessage = throwable.message ?: SOMETHING_WRONG
                        )
                    }
                    else -> {
                        mErrorMsg = decodeError(response.errorBody())!!
                        ResponseStatus.Error(errorMessage = mErrorMsg)
                    }
                }
            }
        }
    }

    private fun decodeError(errorBody: ResponseBody?): String? {
        if (errorBody == null) {
            return ""
        }
        var reader: BufferedReader? = null
        val sb = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(errorBody.byteStream()))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val finallyError = sb.toString()
        try {
            val jObjError = JSONObject(finallyError)
            var message = jObjError.getString("message")
            if (message.contains("No message available")) {
                message = jObjError.getString("error")
            }
            return message
        } catch (e: JSONException) {
            mErrorMsg = sb.toString()
            if (mErrorMsg.isEmpty()) {
                mErrorMsg = SOMETHING_WRONG
            }
            return mErrorMsg
            e.printStackTrace()
        }
    }

    companion object {
        private var isNetworkAvailable: Boolean = false

        fun setNetworkConnectivityStatus(networkAvailable: Boolean) {
            isNetworkAvailable = networkAvailable
        }
    }
}




