package com.defneersungur.a487project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderAdapter(
    private val context: Context,
    private val orders: List<ProductEntity> // Sipariş edilen ürünlerin listesi
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        // Verileri bağla
        holder.orderName.text = order.name
        holder.orderPrice.text = "$${order.price}"
        holder.orderCount.text = "Count: ${order.count}"

        // Glide ile görseli yükle
        Glide.with(context)
            .load(order.imageUrl)
            .into(holder.orderImage)
    }

    override fun getItemCount(): Int = orders.size

    // ViewHolder
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderImage: ImageView = itemView.findViewById(R.id.OrderImage)
        val orderName: TextView = itemView.findViewById(R.id.OrderName)
        val orderPrice: TextView = itemView.findViewById(R.id.orderPrice)
        val orderCount: TextView = itemView.findViewById(R.id.orderCount)
    }
}