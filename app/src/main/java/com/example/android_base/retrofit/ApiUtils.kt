package com.example.android_base.retrofit

import com.example.android_base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiUtils {
    object ApiClient {
        private var mRetrofit: Retrofit? = null

        private var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val client: Retrofit?
            get() {
                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(mOkHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return mRetrofit
            }
    }
}

