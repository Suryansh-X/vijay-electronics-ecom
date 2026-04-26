package com.vijayelectronics.admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijayelectronics.admin.databinding.ItemProductBinding
import com.vijayelectronics.admin.network.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onAction: (Product, String) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productCategory.text = product.category
            binding.productPrice.text = "₹${product.price}"
            binding.productQty.text = "Stock: ${product.quantity}"
            binding.featuredBadge.text = if (product.isFeatured) "⭐ Featured" else "Regular"

            Glide.with(binding.root.context)
                .load(product.photo)
                .into(binding.productImage)

            binding.editButton.setOnClickListener {
                onAction(product, "edit")
            }

            binding.deleteButton.setOnClickListener {
                onAction(product, "delete")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
