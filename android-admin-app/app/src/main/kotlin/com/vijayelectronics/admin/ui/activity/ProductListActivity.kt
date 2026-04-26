package com.vijayelectronics.admin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vijayelectronics.admin.databinding.ActivityProductListBinding
import com.vijayelectronics.admin.network.ApiService
import com.vijayelectronics.admin.network.Product
import com.vijayelectronics.admin.ui.adapter.ProductAdapter
import com.vijayelectronics.admin.util.RetrofitClient
import com.vijayelectronics.admin.util.SharedPrefManager
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var apiService: ApiService
    private lateinit var prefManager: SharedPrefManager
    private lateinit var adapter: ProductAdapter
    private val products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getApiService()
        prefManager = SharedPrefManager(this)

        setupRecyclerView()
        loadProducts()

        binding.addProductButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(products) { product, action ->
            when (action) {
                "edit" -> {
                    val intent = Intent(this, EditProductActivity::class.java)
                    intent.putExtra("product_id", product._id)
                    startActivity(intent)
                }
                "delete" -> deleteProduct(product._id)
            }
        }
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = adapter
    }

    private fun loadProducts() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = apiService.getAllProducts()
                if (response.isSuccessful && response.body()?.success == true) {
                    products.clear()
                    products.addAll(response.body()?.products ?: emptyList())
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProductListActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
            }
        }
    }

    private fun deleteProduct(productId: String) {
        val token = "Bearer ${prefManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = apiService.deleteProduct(productId, token)
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@ProductListActivity, "Product deleted", Toast.LENGTH_SHORT).show()
                    loadProducts()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProductListActivity, "Error deleting product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadProducts()
    }
}
