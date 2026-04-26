package com.vijayelectronics.admin.network

import retrofit2.Response
import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val success: Boolean, val token: String, val admin: AdminData)
data class AdminData(val _id: String, val name: String, val email: String, val role: String)

data class Product(
    val _id: String,
    val name: String,
    val category: String,
    val price: Int,
    val quantity: Int,
    val photo: String,
    val description: String,
    val specs: Map<String, Any>,
    val isFeatured: Boolean
)

data class CreateProductRequest(
    val name: String,
    val category: String,
    val price: Int,
    val quantity: Int,
    val photo: String,
    val description: String = "",
    val specs: Map<String, Any> = emptyMap(),
    val isFeatured: Boolean = false
)

data class UpdatePriceRequest(val price: Int)
data class UpdateQtyRequest(val quantity: Int)

interface ApiService {
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<AdminResponse>

    // Products
    @GET("products")
    suspend fun getAllProducts(): Response<ProductsResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<SingleProductResponse>

    @POST("products")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body product: CreateProductRequest
    ): Response<ProductResponse>

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body product: CreateProductRequest
    ): Response<ProductResponse>

    @PATCH("products/{id}/price")
    suspend fun updatePrice(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body request: UpdatePriceRequest
    ): Response<MessageResponse>

    @PATCH("products/{id}/qty")
    suspend fun updateQuantity(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body request: UpdateQtyRequest
    ): Response<MessageResponse>

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<MessageResponse>

    // Stats
    @GET("admin/stats")
    suspend fun getStats(@Header("Authorization") token: String): Response<StatsResponse>
}

data class AdminResponse(val success: Boolean, val admin: AdminData)
data class ProductsResponse(val success: Boolean, val products: List<Product>, val total: Int)
data class SingleProductResponse(val success: Boolean, val product: Product)
data class ProductResponse(val success: Boolean, val message: String, val product: Product)
data class MessageResponse(val success: Boolean, val message: String)
data class StatsResponse(
    val success: Boolean,
    val stats: Stats
)
data class Stats(
    val totalProducts: Int,
    val totalOrders: Int,
    val totalRevenue: Int,
    val lowStockCount: Int
)
