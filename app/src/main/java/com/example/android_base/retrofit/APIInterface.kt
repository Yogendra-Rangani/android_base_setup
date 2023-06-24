package com.example.android_base.retrofit

import com.example.android_base.model.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {
//    @Headers(ACCEPT, CONTENT_TYPE)
//    @POST(BuildConfig.INVOICE + ALL_TRANSACTION + CORPORATION + "{corID}")
//    suspend fun allTransactionsIF(
//        @Header(AUTHORIZATION) token: String,
//        @Header(REMOTE_ADDRESS_KEY) remoteAddress: String,
//        @Path("corID") corID: Int?,
//        @Query("page") page: Int?,
//        @Query("size") size: Int?,
//        @Body parameter: HashMap<String, Any?>
//    ): Response<TransactionModel>

    @GET("photos")
    suspend fun getPost(): Response<List<PostResponse>>
}