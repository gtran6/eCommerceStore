package com.myprojects.ecommercestore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.model.ApiResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val productRepo: ProductRepo) : ViewModel() {

    val allAddedItems: LiveData<List<ApiResponseItem>> = productRepo.getAllItems()

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