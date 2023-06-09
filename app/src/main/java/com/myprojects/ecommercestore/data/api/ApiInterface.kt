package com.myprojects.ecommercestore.data.api

import com.myprojects.ecommercestore.model.ApiResponse
import com.myprojects.ecommercestore.model.ApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/products")
    suspend fun getProductList(
        @Query("sort") sort: String
    ) : ApiResponse

    @GET("/products/8")
    suspend fun getSingleProduct() : ApiResponseItem

    @GET("/products/category/jewelery")
    suspend fun getAllJewelery() : ApiResponse

    @GET("/products/category/electronics")
    suspend fun getAllElectronic() : ApiResponse

    @GET("/products/category/women's%20clothing")
    suspend fun getWomenClothing() : ApiResponse

    @GET("/products/category/men's%20clothing")
    suspend fun getMenClothing() : ApiResponse
}