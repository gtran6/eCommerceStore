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
    val cartItems: MutableLiveData<MutableList<ApiResponseItem>> = MutableLiveData()

    init {
        cartItems.value = mutableListOf()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItemToCart(item: ApiResponseItem) {
        viewModelScope.launch {
            val currentCartItems = cartItems.value ?: mutableListOf()
            val existingItemIndex = currentCartItems.indexOfFirst { it.id == item.id }
            if (existingItemIndex != -1) {
                val existingItem = currentCartItems[existingItemIndex]
                existingItem.quantity++
            } else {
                item.quantity = 1
                currentCartItems.add(item)
            }
            cartItems.value = currentCartItems // Notify observers about the updated list
            productRepo.updateItems(currentCartItems) // Update the items in the repository
            cartAdapter.notifyDataSetChanged() // Notify the adapter about the data change
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