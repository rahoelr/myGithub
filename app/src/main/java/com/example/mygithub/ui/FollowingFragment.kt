package com.example.mygithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithub.R
import com.example.mygithub.data.response.FollowingResponseItem
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FollowingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)
        recyclerView = binding.rvFollowing
        adapter = FollowingAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        getFollowingUser()
        getFollowerUser()


        if (position == 1) {
            getFollowerUser()
        } else {
            getFollowingUser()
        }

        return view
    }

    private fun getFollowerUser() {
        val username = arguments?.getString(ARG_USERNAME)

        // Buat instance API Retrofit Anda
        val apiService = ApiConfig.getApiService()

        // Panggil API untuk mendapatkan data following
        apiService.getFollowers(username.toString())
            .enqueue(object : Callback<List<FollowingResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowingResponseItem>>,
                    response: Response<List<FollowingResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val followingList = response.body()
                        if (followingList != null) {
                            // Isi adapter dengan data following
                            adapter.submitList(followingList)
                        }
                    }
                }

                override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                    // Handle kesalahan jika terjadi
                }
            })
    }

    private fun getFollowingUser() {
        showLoading(true)
        val username = arguments?.getString(ARG_USERNAME)

        val apiService = ApiConfig.getApiService()

        apiService.getFollowing(username.toString())
            .enqueue(object : Callback<List<FollowingResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowingResponseItem>>,
                    response: Response<List<FollowingResponseItem>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val followingList = response.body()
                        if (followingList != null) {
                            // Isi adapter dengan data following
                            adapter.submitList(followingList)
                        }
                    }
                }

                override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                    showLoading(false)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
