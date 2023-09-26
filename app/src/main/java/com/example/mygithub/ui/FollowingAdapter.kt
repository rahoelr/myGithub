package com.example.mygithub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.data.response.FollowingResponseItem
import com.example.mygithub.databinding.ItemReviewBinding

class FollowingAdapter :
    ListAdapter<FollowingResponseItem, FollowingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: FollowingResponseItem) {
            binding.tvItem.text = review.login
            binding.idTvUser.text = review.id.toString()

            Glide.with(binding.root.context)
                .load(review.avatarUrl)
                .into(binding.itemImageUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowingResponseItem,
                newItem: FollowingResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowingResponseItem,
                newItem: FollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
