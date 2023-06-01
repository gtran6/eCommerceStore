package com.myprojects.ecommercestore.di

import com.myprojects.ecommercestore.data.api.ApiInterface
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.utils.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAllProductRepo(apiInterface: ApiInterface) : ProductRepo = ProductRepo(apiInterface)

    @Singleton
    @Provides
    fun provideRetrofitInstance() : ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}