package com.myprojects.ecommercestore.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.model.ApiResponse
import com.myprojects.ecommercestore.model.ApiResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val productRepo: ProductRepo) : ViewModel() {

    val allProductData: MutableLiveData<List<ApiResponseItem>> = MutableLiveData()
    val singleProductData: MutableLiveData<ApiResponseItem> = MutableLiveData()
    val allJeweleryData: MutableLiveData<List<ApiResponseItem>> = MutableLiveData()
    val allElectronicData: MutableLiveData<List<ApiResponseItem>> = MutableLiveData()
    val womenClothingData: MutableLiveData<List<ApiResponseItem>> = MutableLiveData()
    val menClothingData: MutableLiveData<List<ApiResponseItem>> = MutableLiveData()

    fun getAllProductData(sort: String) {
        viewModelScope.launch {
            productRepo.getAllProductsFromServer(sort).catch {
                Log.e("api-products", "get: ${it.localizedMessage}")
            }.collect { list ->
                allProductData.value = list
            }
        }
    }

    fun getAllJeweleryData() {
        viewModelScope.launch {
            productRepo.getAllJeweleryFromServer().catch {
                Log.e("api-jewelery", "get: ${it.localizedMessage}")
            }.collect { list ->
                allJeweleryData.value = list
            }
        }
    }

    fun getAllElectronicData() {
        viewModelScope.launch {
            productRepo.getAllElectronicFromServer().catch {
                Log.e("api-electronic", "get: ${it.localizedMessage}")
            }.collect { list ->
                allElectronicData.value = list
            }
        }
    }

    fun getWomenClothingData() {
        viewModelScope.launch {
            productRepo.getWomenClothingFromServer().catch {
                Log.e("api-women", "get: ${it.localizedMessage}")
            }.collect { list ->
                womenClothingData.value = list
            }
        }
    }

    fun getMenClothingData() {
        viewModelScope.launch {
            productRepo.getMenClothingFromServer().catch {
                Log.e("api-men", "get: ${it.localizedMessage}")
            }.collect { list ->
                menClothingData.value = list
            }
        }
    }

    fun getSingleProductData() {
        viewModelScope.launch {
            productRepo.getSingleProductFromServer().catch {
                Log.e("api-single-product", "get: ${it.localizedMessage}")
            }.collect { product ->
                singleProductData.value = product
            }
        }
    }

    fun addItemToFavorites(item: ApiResponseItem) {
        viewModelScope.launch {
            productRepo.insertItem(item)
        }
    }
}