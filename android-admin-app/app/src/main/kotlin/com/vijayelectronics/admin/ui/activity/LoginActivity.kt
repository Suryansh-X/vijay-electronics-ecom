package com.vijayelectronics.admin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vijayelectronics.admin.R
import com.vijayelectronics.admin.databinding.ActivityLoginBinding
import com.vijayelectronics.admin.network.*
import com.vijayelectronics.admin.util.RetrofitClient
import com.vijayelectronics.admin.util.SharedPrefManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService
    private lateinit var prefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getApiService()
        prefManager = SharedPrefManager(this)

        // Check if already logged in
        if (prefManager.isLoggedIn()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        // Set default credentials for demo
        binding.emailEditText.setText("admin@vijayelectronics.com")
        binding.passwordEditText.setText("Admin@123")

        binding.loginButton.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        binding.loginButton.isEnabled = false
        binding.loginButton.text = "Logging in..."

        lifecycleScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body()?.success == true) {
                    val token = response.body()?.token ?: ""
                    val admin = response.body()?.admin

                    // Save to SharedPreferences
                    prefManager.saveLoginData(token, admin?.email ?: "", admin?.name ?: "")

                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    val error = response.body()?.let { "Login failed" } ?: "Network error"
                    Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loginButton.isEnabled = true
                binding.loginButton.text = "Login"
            }
        }
    }
}
