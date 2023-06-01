package com.myprojects.ecommercestore.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myprojects.ecommercestore.model.ApiResponseItem

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ApiResponseItem)

    @Delete
    suspend fun delete(item: ApiResponseItem)

    @Query("SELECT * FROM product_table")
    fun getFavoriteItems() : LiveData<List<ApiResponseItem>>
}