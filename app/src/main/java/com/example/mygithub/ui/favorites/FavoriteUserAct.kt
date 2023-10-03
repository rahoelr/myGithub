package com.example.mygithub.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.data.ViewModelFactory
import com.example.mygithub.databinding.ActivityFavoriteUserBinding
import com.example.mygithub.repository.FavoriteRepository
import com.example.mygithub.ui.DetailViewModel
import com.example.mygithub.ui.FollowingViewModel

class FavoriteUserAct : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(application)).get(DetailViewModel::class.java)

        val recyclerView = binding.rvFavorites

        adapter = FavoriteUserAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {

        val favoriteRepository = FavoriteRepository(application)

        favoriteRepository.getAllFavorites().observe(this, { favoriteUsers ->
            adapter.setFavorites(favoriteUsers)
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
