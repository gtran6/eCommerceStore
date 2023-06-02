package com.myprojects.ecommercestore.model
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "product_table")
data class ApiResponseItem(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String,
    var quantity: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        Gson().fromJson(parcel.readString(), Rating::class.java),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeDouble(price)
        parcel.writeString(Gson().toJson(rating))
        parcel.writeString(title)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiResponseItem> {
        override fun createFromParcel(parcel: Parcel): ApiResponseItem {
            return ApiResponseItem(parcel)
        }

        override fun newArray(size: Int): Array<ApiResponseItem?> {
            return arrayOfNulls(size)
        }
    }
}