package com.example.android_base.retrofit

import com.example.android_base.model.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {
//    @GET("api/")
//    suspend fun wallpaperIF(
//        @Query("key") key: String,
//        @Query("q") query: String,
//        @Query("image_type") type: String,
//        @Query("pretty") pretty: Boolean
//    ): Response<WallPaperResponse>

    @GET("photos")
    suspend fun getPost(): Response<List<PostResponse>>
}