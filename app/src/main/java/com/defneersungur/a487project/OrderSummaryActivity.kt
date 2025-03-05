package com.defneersungur.a487project

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OrderSummaryActivity : AppCompatActivity() {

    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var buttonProceedToPayment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        // RecyclerView'i bağla
        orderRecyclerView = findViewById(R.id.orderRecyclerView)

        // Intent'ten gelen sipariş listesi
        val selectedProducts = intent.getParcelableArrayListExtra<ProductEntity>("selectedProducts") ?: emptyList()

        // Adapter ve RecyclerView ayarları
        orderAdapter = OrderAdapter(this, selectedProducts)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.adapter = orderAdapter

        // Geri dönmek için butonu bulun
        val backToMainButton: ImageView = findViewById(R.id.backArrow)
        backToMainButton.setOnClickListener {
            finish() // Bu aktiviteyi kapat ve önceki aktiviteye dön
        }

        buttonProceedToPayment = findViewById(R.id.buttonProceedToPayment)

        buttonProceedToPayment.setOnClickListener {
            showSimpleDialog()
        }

    }

    private fun showSimpleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm Order")
            .setMessage("Do you want to proceed with this order")
            .setNegativeButton("Cancel") { dialog, _ ->
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setPositiveButton("OK") { dialog, _ ->
                Toast.makeText(this, "Confirmed", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .show()
    }
}