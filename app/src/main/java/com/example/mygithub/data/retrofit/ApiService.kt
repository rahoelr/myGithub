package com.example.mygithub.data.retrofit

import com.example.mygithub.data.response.DetailUserResponse
import com.example.mygithub.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
}
