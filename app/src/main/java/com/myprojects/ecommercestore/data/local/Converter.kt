package com.myprojects.ecommercestore.data.local

import androidx.room.TypeConverter
import com.myprojects.ecommercestore.model.Rating

class Converter {

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return "${rating.rate},${rating.count}"
    }

    @TypeConverter
    fun toRating(value: String): Rating {
        val parts = value.split(",")
        val rate = parts[0].toDouble()
        val count = parts[1].toInt()
        return Rating(count, rate)
    }
}