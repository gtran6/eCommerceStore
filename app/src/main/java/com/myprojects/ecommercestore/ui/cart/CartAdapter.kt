package com.myprojects.ecommercestore.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprojects.ecommercestore.databinding.ItemCartBinding
import com.myprojects.ecommercestore.model.ApiResponseItem

class CartAdapter(val list: List<ApiResponseItem>, val onCancelClickListener: OnCancelClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(var binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnCancelClickListener {
        fun OnCancelClick(item: ApiResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(item.image).into(image)
            price.text = "$${item.price}"
            title.text = item.title
            category.text = item.category
            tvItemCount.text = item.quantity.toString()

            cancel.setOnClickListener {
                onCancelClickListener.OnCancelClick(item)
            }
        }
    }
}