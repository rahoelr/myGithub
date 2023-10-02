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