package com.defneersungur.a487project.WorkPackage

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defneersungur.a487project.ProductEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ProductWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        return try {
            // Gelen sepet verisini al
            val jsonCartProducts = inputData.getString("cartProducts")

            if (jsonCartProducts.isNullOrEmpty()) {
                Log.e("CartProcessingWorker", "Sepet verisi boş!")
                return Result.failure()
            }

            // JSON'u ürün listesine dönüştür
            val cartProducts: List<ProductEntity> = Gson().fromJson(jsonCartProducts, object : TypeToken<List<ProductEntity>>() {}.type)

            // Sepetteki ürünlerin toplam fiyatını hesapla
            val totalCost = cartProducts.sumOf { it.price * it.count }

            // Hesaplanan toplam fiyatı logla
            Log.d("CartProcessingWorker", "Sepetteki toplam ürün sayısı: ${cartProducts.size}, Toplam maliyet: $$totalCost")

            // İşlem başarılı
            Result.success()
        } catch (e: Exception) {
            Log.e("CartProcessingWorker", "Worker çalışırken hata oluştu: ${e.message}", e)
            Result.failure()
        }
    }
}