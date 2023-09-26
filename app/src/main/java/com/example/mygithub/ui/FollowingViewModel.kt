package com.example.mygithub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.FollowingResponseItem
import com.example.mygithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _followerList = MutableLiveData<List<FollowingResponseItem>?>()
    val followerList: MutableLiveData<List<FollowingResponseItem>?>
        get() = _followerList

    private val _followingList = MutableLiveData<List<FollowingResponseItem>?>()
    val followingList: MutableLiveData<List<FollowingResponseItem>?>
        get() = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadFollowerUser(username: String) {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService()
        apiService.getFollowers(username)
            .enqueue(object : Callback<List<FollowingResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowingResponseItem>>,
                    response: Response<List<FollowingResponseItem>>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        val followerList = response.body()
                        _followerList.value = followerList
                    }
                }

                override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                }
            })
    }

    fun loadFollowingUser(username: String) {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService()
        apiService.getFollowing(username)
            .enqueue(object : Callback<List<FollowingResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowingResponseItem>>,
                    response: Response<List<FollowingResponseItem>>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        val followingList = response.body()
                        _followingList.value = followingList
                    }
                }

                override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                }
            })
    }
}