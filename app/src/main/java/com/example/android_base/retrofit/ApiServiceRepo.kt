package com.example.android_base.retrofit

import android.app.Activity
import com.example.android_base.model.PostResponse
import javax.inject.Inject

class ApiServiceRepo @Inject constructor(private val apiInterface: APIInterface) : ApiResponseHandler() {

//    suspend fun allTransactionsRepo(
//        token: String?,
//        corID: Int?,
//        remoteAddress: String,
//        parameter: HashMap<String, Any?>,
//        page: Int?,
//        size: Int?,
//    ): ResponseStatus<TransactionModel> {
//        return safeApiCall {
//            APIInterface.getApi()?.allTransactionsIF(
//                token = "Bearer $token",
//                corID = corID,
//                remoteAddress = remoteAddress,
//                page = page,
//                size = size,
//                parameter = parameter,
//            )!!
//        }
//    }

    suspend fun getPostRepo(activity: Activity): ResponseStatus<List<PostResponse>> {
        return safeApiCall {
            apiInterface.getPost()
        }
    }
}