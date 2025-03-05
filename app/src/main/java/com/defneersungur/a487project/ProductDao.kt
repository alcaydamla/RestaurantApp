package com.defneersungur.a487project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Tek bir ürün ekleme
    @Insert
    suspend fun insert(product: ProductEntity)

    // Birden fazla ürünü topluca ekleme
    @Insert
    suspend fun insertAll(products: List<ProductEntity>)

    // Tüm ürünleri alma
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>

    // Tüm ürünleri silme
    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts(): Void

}
