package com.defneersungur.a487project

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product_table") // Room tablosu adı
data class ProductEntity(
    @PrimaryKey
    @SerializedName("id") val id: Int, // JSON'dan gelen id değerini kullanıyoruz
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String,
    var count: Int = 0 // Varsayılan olarak 0
): Parcelable