package com.vijayelectronics.admin.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.vijayelectronics.admin.databinding.ActivityEditProductBinding
import com.vijayelectronics.admin.network.ApiService
import com.vijayelectronics.admin.network.CreateProductRequest
import com.vijayelectronics.admin.network.UpdatePriceRequest
import com.vijayelectronics.admin.network.UpdateQtyRequest
import com.vijayelectronics.admin.util.RetrofitClient
import com.vijayelectronics.admin.util.SharedPrefManager
import kotlinx.coroutines.launch

class EditProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProductBinding
    private lateinit var apiService: ApiService
    private lateinit var prefManager: SharedPrefManager
    private lateinit var productId: String
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getApiService()
        prefManager = SharedPrefManager(this)
        productId = intent.getStringExtra("product_id") ?: return

        loadProductDetails()

        binding.selectImageButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.updateProductButton.setOnClickListener { updateProduct() }
        binding.updatePriceButton.setOnClickListener { updatePrice() }
        binding.updateQtyButton.setOnClickListener { updateQuantity() }
        binding.backButton.setOnClickListener { finish() }
    }

    private fun loadProductDetails() {
        lifecycleScope.launch {
            try {
                val response = apiService.getProduct(productId)
                if (response.isSuccessful && response.body()?.success == true) {
                    val product = response.body()?.product
                    product?.let {
                        binding.productNameEditText.setText(it.name)
                        binding.categorySpinner.setSelection(getCategory(it.category))
                        binding.priceEditText.setText(it.price.toString())
                        binding.quantityEditText.setText(it.quantity.toString())
                        binding.descriptionEditText.setText(it.description)
                        binding.featuredCheckBox.isChecked = it.isFeatured
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProductActivity, "Error loading product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProduct() {
        val name = binding.productNameEditText.text.toString().trim()
        val category = binding.categorySpinner.selectedItem.toString()
        val price = binding.priceEditText.text.toString().toIntOrNull() ?: 0
        val quantity = binding.quantityEditText.text.toString().toIntOrNull() ?: 0
        val description = binding.descriptionEditText.text.toString()
        val isFeatured = binding.featuredCheckBox.isChecked

        val token = "Bearer ${prefManager.getToken()}"
        lifecycleScope.launch {
            try {
                val request = CreateProductRequest(
                    name = name,
                    category = category,
                    price = price,
                    quantity = quantity,
                    photo = selectedImageUri?.toString() ?: "",
                    description = description,
                    isFeatured = isFeatured
                )
                val response = apiService.updateProduct(productId, token, request)
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@EditProductActivity, "Product updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProductActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePrice() {
        val price = binding.priceEditText.text.toString().toIntOrNull() ?: return
        val token = "Bearer ${prefManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = apiService.updatePrice(productId, token, UpdatePriceRequest(price))
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@EditProductActivity, "Price updated in real-time ✓", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProductActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateQuantity() {
        val quantity = binding.quantityEditText.text.toString().toIntOrNull() ?: return
        val token = "Bearer ${prefManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = apiService.updateQuantity(productId, token, UpdateQtyRequest(quantity))
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@EditProductActivity, "Inventory updated in real-time ✓", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProductActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCategory(category: String): Int {
        return when (category) {
            "TVs" -> 0
            "Washing Machine" -> 1
            "Geyser" -> 2
            "AC" -> 3
            "Cooler" -> 4
            "Oven" -> 5
            "Other Devices" -> 6
            "Music" -> 7
            else -> 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.selectedImageTextView.text = "Image selected ✓"
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
    }
}
