package com.example.mygithub.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.database.FavoriteUser
import com.example.mygithub.databinding.ItemReviewBinding
import com.example.mygithub.ui.DetailAct

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private val listFavorites = ArrayList<FavoriteUser>()

    fun setFavorites(favorites: List<FavoriteUser>) {
        listFavorites.clear()
        listFavorites.addAll(favorites)
        notifyDataSetChanged()
    }

    class FavoriteUserViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteUser) {
            with(binding) {
                tvItem.text = favorites.login
                idTvUser.text = favorites.id.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailAct::class.java)
                    intent.putExtra(DetailAct.ARG_USERNAME, favorites.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .into(binding.itemImageUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favorites = listFavorites[position]
        holder.bind(favorites)
    }
}
