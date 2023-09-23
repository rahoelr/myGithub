package com.example.mygithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.data.response.DetailUserResponse
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("userName")
        if (username != null) {
            getDetailUser(username)
        }
    }

    private fun getDetailUser(username: String) {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        val call = apiService.getDetailUser(username)

        call.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse != null) {
                        // Update UI with user details
                        binding.tvDetailUser.text = userResponse.login
                        binding.tvDetailUser2.text = userResponse.name
                        binding.tvFollowers.text = userResponse.followers.toString()
                        binding.tvFollowing.text = userResponse.following.toString()
                        Glide.with(binding.root.context)
                            .load(userResponse.avatarUrl)
                            .into(binding.itemImageDetailUser)
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}