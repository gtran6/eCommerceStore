package com.myprojects.ecommercestore.di

import android.content.Context
import com.myprojects.ecommercestore.data.api.ApiInterface
import com.myprojects.ecommercestore.data.local.AppDatabase
import com.myprojects.ecommercestore.data.local.FavoriteDao
import com.myprojects.ecommercestore.data.repo.ProductRepo
import com.myprojects.ecommercestore.utils.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAllProductRepo(apiInterface: ApiInterface, favoriteDao: FavoriteDao) : ProductRepo = ProductRepo(apiInterface, favoriteDao)

    @Singleton
    @Provides
    fun provideRetrofitInstance() : ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context) = AppDatabase.getItemDatabase(applicationContext)
}