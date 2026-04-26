package com.vijayelectronics.admin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vijayelectronics.admin.databinding.ActivityDashboardBinding
import com.vijayelectronics.admin.network.ApiService
import com.vijayelectronics.admin.util.RetrofitClient
import com.vijayelectronics.admin.util.SharedPrefManager
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var apiService: ApiService
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getApiService()
        prefManager = SharedPrefManager(this)

        // Check authentication
        if (!prefManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupUI()
        loadStats()
    }

    private fun setupUI() {
        binding.adminNameTextView.text = "Welcome, ${prefManager.getAdminName()}"

        binding.productsCardView.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        binding.addProductButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            prefManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun loadStats() {
        val token = "Bearer ${prefManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = apiService.getStats(token)
                if (response.isSuccessful && response.body()?.success == true) {
                    val stats = response.body()?.stats
                    binding.totalProductsTextView.text = stats?.totalProducts.toString()
                    binding.totalOrdersTextView.text = stats?.totalOrders.toString()
                    binding.totalRevenueTextView.text = "₹${stats?.totalRevenue}"
                    binding.lowStockTextView.text = stats?.lowStockCount.toString()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity, "Error loading stats", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
