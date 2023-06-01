package com.myprojects.ecommercestore.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.ItemFavBinding
import com.myprojects.ecommercestore.model.ApiResponseItem

class FavoriteAdapter(val list: List<ApiResponseItem>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(var binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(item.image).into(phonePreviewImageView)
            newPriceTextView.text = "$${item.price}"
            brandTextView.text = item.title
        }
    }
}