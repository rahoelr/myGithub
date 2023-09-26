package com.example.mygithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.lifecycle.ViewModelProvider

class DetailAct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

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
        if (username != null) {
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

            viewModel.loadUserDetail(username)

            viewModel.userDetail.observe(this, Observer { userDetail ->
                binding.tvDetailUser.text = userDetail?.login
                binding.tvDetailUser2.text = userDetail?.name
                binding.tvFollowers.text = userDetail?.followers.toString()
                binding.tvFollowing.text = userDetail?.following.toString()
                Glide.with(binding.root.context)
                    .load(userDetail?.avatarUrl)
                    .into(binding.itemImageDetailUser)
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
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}