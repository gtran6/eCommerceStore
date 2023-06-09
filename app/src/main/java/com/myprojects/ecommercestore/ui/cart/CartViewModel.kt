package com.myprojects.ecommercestore.ui.cart

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.model.ApiResponseItem
import com.myprojects.ecommercestore.utils.combineLatest
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

    val subtotal: LiveData<Double> = allAddedItems.map { items ->
        var total = 0.0
        for (item in items) {
            total += item.price * item.quantity
        }
        total
    }

    val tax: LiveData<Double> = subtotal.map { subTotal ->
        val taxRate = 0.0525
        val taxValue = subTotal * taxRate
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.format(taxValue).toDouble()
    }

    val shipping: LiveData<Double> = MutableLiveData<Double>().apply {
        value = 6.0
    }

    val bagTotal: LiveData<Double> = combineLatest(subtotal, tax, shipping) { subTotal, tax, shipping ->
        subTotal + tax + shipping
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
            cartItems.value = currentCartItems
            productRepo.updateItems(currentCartItems)
            cartAdapter.notifyDataSetChanged()
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