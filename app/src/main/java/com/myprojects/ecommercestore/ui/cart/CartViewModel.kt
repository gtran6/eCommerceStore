package com.myprojects.ecommercestore.ui.cart

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.model.ApiResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val productRepo: ProductRepo, val cartAdapter: CartAdapter) : ViewModel() {

    val allAddedItems: LiveData<List<ApiResponseItem>> = productRepo.getAllItems()
    private val cartItems: MutableList<ApiResponseItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addItemToCart(item: ApiResponseItem) {
        viewModelScope.launch {
            val existingItem = cartItems.find { it.id == item.id }
            if (existingItem != null) {
                existingItem.quantity++
            } else {
                item.quantity = 1
                cartItems.add(item)
            }
            cartAdapter.notifyDataSetChanged()
            productRepo.updateItems(cartItems)
        }
    }
    fun addItem(item: ApiResponseItem) {
        viewModelScope.launch {
            productRepo.insertItem(item)
        }
    }

    fun deleteItem(item: ApiResponseItem) {
        viewModelScope.launch {
            productRepo.deleteItem(item)
        }
    }
}