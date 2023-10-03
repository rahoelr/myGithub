package com.example.mygithub.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.DetailUserResponse
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.database.FavoriteUser
import com.example.mygithub.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse?>()
    val userDetail: MutableLiveData<DetailUserResponse?>
        get() = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val mFavoriteUserRepository: FavoriteRepository =
        FavoriteRepository(application)

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(id: Int) {
        mFavoriteUserRepository.delete(id)
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()

    fun loadUserDetail(username: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val call = apiService.getDetailUser(username)

        call.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse != null) {
                        _userDetail.value = userResponse
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}