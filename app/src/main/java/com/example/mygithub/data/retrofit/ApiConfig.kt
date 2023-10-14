package com.example.mygithub.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val AUTH_TOKEN = "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"

        fun getApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeader =
                    req.newBuilder().addHeader("Authorization", "token $AUTH_TOKEN").build()
                chain.proceed(requestHeader)
            }
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }

}
