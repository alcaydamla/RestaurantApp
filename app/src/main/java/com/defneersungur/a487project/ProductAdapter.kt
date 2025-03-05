package com.defneersungur.a487project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private val context: Context,
    private val products: List<ProductEntity>, // MainActivity'deki ürünlerin referansı
    private val onItemCountChanged: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.RecyclerViewItemHolder>() {

    private var recyclerItemValues = emptyList<ProductEntity>()

    fun setData(items: List<ProductEntity>) {
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_product, parent, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]

        holder.productName.text = item.name
        holder.productPrice.text = "$${item.price}"
        holder.itemCount.text = item.count.toString()

        Glide.with(context)
            .load(item.imageUrl)
            .into(holder.imgProduct)

        holder.btnAddtoCard.setOnClickListener {
            item.count++
            holder.itemCount.text = item.count.toString()
            onItemCountChanged(1)
            notifyItemChanged(position)
        }

        holder.plusIcon.setOnClickListener {
            item.count++
            holder.itemCount.text = item.count.toString()
            onItemCountChanged(1)
            notifyItemChanged(position)
        }

        holder.minusIcon.setOnClickListener {
            if (item.count > 0) {
                item.count--
                holder.itemCount.text = item.count.toString()
                onItemCountChanged(-1)
            } else {
                Toast.makeText(context, "Cannot reduce below 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = recyclerItemValues.size

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.OrderName)
        val productPrice: TextView = itemView.findViewById(R.id.orderPrice)
        val btnAddtoCard: Button = itemView.findViewById(R.id.addToCartButton)
        val imgProduct: ImageView = itemView.findViewById(R.id.OrderImage)
        val itemCount: TextView = itemView.findViewById(R.id.itemCount)
        val plusIcon: ImageView = itemView.findViewById(R.id.plusIcon)
        val minusIcon: ImageView = itemView.findViewById(R.id.minusIcon)
        val trashIcon: ImageView = itemView.findViewById(R.id.trashIcon)
    }
}