package com.myprojects.ecommercestore.data.repo

import androidx.lifecycle.LiveData
import com.myprojects.ecommercestore.data.api.ApiInterface
import com.myprojects.ecommercestore.data.local.FavoriteDao
import com.myprojects.ecommercestore.model.ApiResponse
import com.myprojects.ecommercestore.model.ApiResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepo @Inject constructor(val apiInterface: ApiInterface, val favDao: FavoriteDao) {

    fun getAllProductsFromServer(sort: String) : Flow<ApiResponse> = flow {
        emit(apiInterface.getProductList(sort))
    }

    fun getSingleProductFromServer() : Flow<ApiResponseItem> = flow {
        emit(apiInterface.getSingleProduct())
    }

    fun getAllJeweleryFromServer() : Flow<ApiResponse> = flow {
        emit(apiInterface.getAllJewelery())
    }

    fun getAllElectronicFromServer() : Flow<ApiResponse> = flow {
        emit(apiInterface.getAllElectronic())
    }

    fun getWomenClothingFromServer() : Flow<ApiResponse> = flow {
        emit(apiInterface.getWomenClothing())
    }

    fun getMenClothingFromServer() : Flow<ApiResponse> = flow {
        emit(apiInterface.getMenClothing())
    }

    suspend fun insertItem(item: ApiResponseItem) = favDao.insert(item)

    suspend fun deleteItem(item: ApiResponseItem) = favDao.delete(item)

    fun getAllItems(): LiveData<List<ApiResponseItem>> = favDao.getFavoriteItems()

    suspend fun updateItems(items: List<ApiResponseItem>) = favDao.update(items)
}