package com.example.android_base.retrofit

import android.app.Activity
import com.example.android_base.model.PostResponse
import javax.inject.Inject

class ApiServiceRepo @Inject constructor(private val apiInterface: APIInterface) : ApiResponseHandler() {

    suspend fun getPostRepo(activity: Activity): ResponseStatus<List<PostResponse>> {
        return safeApiCall {
            apiInterface.getPost()
        }
    }

//    fun getPostRepo(query: String): ResponseStatus<List<PostResponse>> {
//        return null
//    }
}