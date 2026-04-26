package com.vijayelectronics.admin.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "VijayElectronicsAdmin",
        Context.MODE_PRIVATE
    )

    fun saveLoginData(token: String, email: String, name: String) {
        prefs.edit().apply {
            putString("token", token)
            putString("email", email)
            putString("name", name)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    fun getToken(): String = prefs.getString("token", "") ?: ""

    fun getAdminEmail(): String = prefs.getString("email", "") ?: ""

    fun getAdminName(): String = prefs.getString("name", "Admin") ?: "Admin"

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
