package com.myprojects.ecommercestore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.myprojects.ecommercestore.model.ApiResponseItem

@Database(entities = [ApiResponseItem::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getItemDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "item-database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}