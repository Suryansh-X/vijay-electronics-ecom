package com.vijayelectronics.admin.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.vijayelectronics.admin.databinding.ActivityAddProductBinding
import com.vijayelectronics.admin.network.ApiService
import com.vijayelectronics.admin.network.CreateProductRequest
import com.vijayelectronics.admin.util.RetrofitClient
import com.vijayelectronics.admin.util.SharedPrefManager
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var apiService: ApiService
    private lateinit var prefManager: SharedPrefManager
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getApiService()
        prefManager = SharedPrefManager(this)

        binding.selectImageButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.saveProductButton.setOnClickListener {
            saveProduct()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveProduct() {
        val name = binding.productNameEditText.text.toString().trim()
        val category = binding.categorySpinner.selectedItem.toString()
        val price = binding.priceEditText.text.toString().toIntOrNull() ?: 0
        val quantity = binding.quantityEditText.text.toString().toIntOrNull() ?: 0
        val description = binding.descriptionEditText.text.toString()
        val photoUrl = selectedImageUri?.toString() ?: ""
        val isFeatured = binding.featuredCheckBox.isChecked

        if (name.isEmpty() || photoUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select image", Toast.LENGTH_SHORT).show()
            return
        }

        binding.saveProductButton.isEnabled = false
        val token = "Bearer ${prefManager.getToken()}"

        lifecycleScope.launch {
            try {
                val request = CreateProductRequest(
                    name = name,
                    category = category,
                    price = price,
                    quantity = quantity,
                    photo = photoUrl,
                    description = description,
                    isFeatured = isFeatured
                )

                val response = apiService.createProduct(token, request)
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AddProductActivity, "Product added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddProductActivity, "Failed to add product", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddProductActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.saveProductButton.isEnabled = true
            }
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
