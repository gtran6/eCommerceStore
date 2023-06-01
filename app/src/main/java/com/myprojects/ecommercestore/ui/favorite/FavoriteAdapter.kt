package com.myprojects.ecommercestore.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.ItemFavBinding
import com.myprojects.ecommercestore.model.ApiResponseItem

class FavoriteAdapter(val list: List<ApiResponseItem>, val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(var binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnDeleteClickListener {
        fun OnDeleteClick(item: ApiResponseItem)
    }

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

            layout.setOnClickListener {  view ->
                val data = FavoriteFragmentDirections.actionNavFavToDetailFragment(item)
                findNavController(view).navigate(data)
            }

            deleteIcon.setOnClickListener {

                val scaleAnimation = ScaleAnimation(
                    1.0f, 0.8f, // Start and end scale X
                    1.0f, 0.8f, // Start and end scale Y
                    Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X
                    Animation.RELATIVE_TO_SELF, 0.5f // Pivot Y
                ).apply {
                    duration = 200
                    repeatMode = Animation.REVERSE
                    repeatCount = 1
                }
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        onDeleteClickListener.OnDeleteClick(item)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                deleteIcon.startAnimation(scaleAnimation)
            }
        }
    }
}