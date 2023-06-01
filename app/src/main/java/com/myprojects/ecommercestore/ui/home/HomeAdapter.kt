package com.myprojects.ecommercestore.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.ItemMainBinding
import com.myprojects.ecommercestore.model.ApiResponseItem

class HomeAdapter(val list: List<ApiResponseItem>, val onFavoriteClickListener: OnFavoriteClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(var binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnFavoriteClickListener {
        fun onFavoriteClick(item: ApiResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(item.image).into(phonePreviewImageView)
            newPriceTextView.text = "$${item.price}"
            brandTextView.text = item.title

            layout.setOnClickListener { view ->
                val data = HomeFragmentDirections.actionNavHomeToDetailFragment(item)
                findNavController(view).navigate(data)
            }
            heartIcon.setOnClickListener {
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
                        onFavoriteClickListener.onFavoriteClick(item)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                heartIcon.startAnimation(scaleAnimation)
            }
        }
    }
}