package com.myprojects.ecommercestore.data.repo

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

    suspend fun insertItem(item: ApiResponseItem) = favDao.insert(item)

    suspend fun deleteItem(item: ApiResponseItem) = favDao.delete(item)

    fun getAllItems() = favDao.getFavoriteItems()
}