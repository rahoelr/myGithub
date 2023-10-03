package com.example.mygithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.data.ViewModelFactory
import com.example.mygithub.database.FavoriteUser
import com.example.mygithub.repository.FavoriteRepository
import com.example.mygithub.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailAct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favoriteRepository: FavoriteRepository

    private var buttonState: Boolean = false
    private var favoriteUser: FavoriteUser? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )

        const val ARG_USERNAME = "userName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(ARG_USERNAME)

        viewModel = ViewModelProvider(this, ViewModelFactory(application)).get(DetailViewModel::class.java)

        if (username != null) {
            viewModel.loadUserDetail(username)

            viewModel.userDetail.observe(this, Observer { userDetail ->
                binding.tvDetailUser.text = userDetail?.login
                binding.tvDetailUser2.text = userDetail?.name
                binding.tvFollowers.text = userDetail?.followers.toString()
                binding.tvFollowing.text = userDetail?.following.toString()
                Glide.with(binding.root.context)
                    .load(userDetail?.avatarUrl)
                    .into(binding.itemImageDetailUser)

                checkIfUserIsFavorite(userDetail?.login, userDetail?.id ?: 0)
            })

            viewModel.isLoading.observe(this, Observer { isLoading ->
                showLoading(isLoading)
            })

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = username
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }

        favoriteRepository = FavoriteRepository(application)
    }

    private fun favoriteToDatabase(favoriteUser: FavoriteUser) {
        favoriteRepository.insert(favoriteUser)
    }

    private fun checkIfUserIsFavorite(username: String?, userId: Int) {
        if (username != null) {
            favoriteRepository.isUserFavorite(username).observe(this, Observer { isFavorite ->
                if (isFavorite) {
                    buttonState = true
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    buttonState = false
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }

                binding.fabFavorite.setOnClickListener {
                    if (isFavorite) {
                        viewModel.delete(userId)
                        showToast("User berhasil dihapus dari favorit")
                    } else {
                        val favoriteUser = FavoriteUser(userId, username)
                        viewModel.insert(favoriteUser)
                        showToast("User berhasil ditambahkan ke favorite")
                    }
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
