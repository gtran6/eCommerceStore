package com.myprojects.ecommercestore.data.repo

import com.myprojects.ecommercestore.data.api.ApiInterface
import com.myprojects.ecommercestore.model.ApiResponse
import com.myprojects.ecommercestore.model.ApiResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepo @Inject constructor(val apiInterface: ApiInterface) {

    fun getAllProductsFromServer(sort: String) : Flow<ApiResponse> = flow {
        emit(apiInterface.getProductList(sort))
    }

    fun getSingleProductFromServer() : Flow<ApiResponseItem> = flow {
        emit(apiInterface.getSingleProduct())
    }
}