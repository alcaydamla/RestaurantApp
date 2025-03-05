package com.defneersungur.a487project

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.defneersungur.a487project.WorkPackage.ProductWorker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var productList: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var trolleyIcon: ImageView
    private lateinit var itemCountTextView: TextView // Sepet sayısını gösteren TextView
    private var itemCount = 0 // Sepetteki toplam ürün sayısı
    private var products: List<ProductEntity> = emptyList()
    private lateinit var searchInput: EditText
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Görünümleri bağla
        productList = findViewById(R.id.productList)
        itemCountTextView = findViewById(R.id.cartBadge)
        trolleyIcon = findViewById(R.id.TrolleyIcon)

        // RecyclerView'i ayarla
        setupRecyclerView()

        // Ürünleri API'den getir
        fetchProductsFromApi()

        //Search Operation
        searchInput = findViewById(R.id.searchBar)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                filterAndReorderProducts(query)
            }
        })

        // TrolleyIcon'a tıklanınca ses çal
        mediaPlayer = MediaPlayer.create(this, R.raw.restoransesi)

        trolleyIcon.setOnClickListener {
            mediaPlayer.start()

            val selectedProducts = products.filter { it.count > 0 }

            if (selectedProducts.isEmpty()) {
                Toast.makeText(this, "Basket empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jsonCartProducts = Gson().toJson(selectedProducts)
            val data = workDataOf("cartProducts" to jsonCartProducts)

            val request = OneTimeWorkRequestBuilder<ProductWorker>()
                .setInputData(data)
                .build()

            WorkManager.getInstance(this).enqueue(request)

            Log.d("MainActivity", "CartProcessingWorker başlatıldı. Sepet verisi gönderildi.")

            val intent = Intent(this, OrderSummaryActivity::class.java)
            intent.putParcelableArrayListExtra("selectedProducts", ArrayList(selectedProducts))
            startActivity(intent)
        }

        fabAdd = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            // Buton tıklanınca yapılacaklar
            Toast.makeText(this, "Please order your food from here it will be in your table in seconds!", Toast.LENGTH_SHORT).show()
        }

        fabAdd.setOnLongClickListener {
            Toast.makeText(this, "You can add your favorite meals to your cart!", Toast.LENGTH_SHORT).show()
            true // Uzun basma olayını işlediğimizi belirtiyoruz
        }
    }

    private fun filterAndReorderProducts(query: String) {
        val filteredProducts = products.filter { it.name.trim().contains(query.trim(), ignoreCase = true) }

        if (filteredProducts.isEmpty()) {
            Toast.makeText(this, "Search Result Could Not Be Found", Toast.LENGTH_SHORT).show()
            return
        }

        val reorderedProducts = filteredProducts + products.filter { !filteredProducts.contains(it) }
        productAdapter.setData(reorderedProducts)
        productList.scrollToPosition(0)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(this, products) { change ->
            updateCartCount(change)
        }
        productList.layoutManager = LinearLayoutManager(this)
        productList.adapter = productAdapter
    }


    private fun fetchProductsFromApi() {
        val recipeService = ProductApi.getProducts().create(ProductService::class.java)
        val request = recipeService.getProduct()

        request.enqueue(object : Callback<List<ProductEntity>> {
            override fun onFailure(call: Call<List<ProductEntity>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<ProductEntity>>, response: Response<List<ProductEntity>>) {
                if (response.isSuccessful) {
                    products = response.body() ?: emptyList()
                    productAdapter.setData(products)
                }
            }
        })
    }

    private fun updateCartCount(change: Int) {
        itemCount += change
        if (itemCount < 0) itemCount = 0 // Negatif değer koruması
        itemCountTextView.text = itemCount.toString() // UI'yi güncelle
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // MediaPlayer'ı serbest bırak
    }
}