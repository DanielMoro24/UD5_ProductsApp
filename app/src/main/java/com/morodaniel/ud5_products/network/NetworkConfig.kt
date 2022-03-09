package com.morodaniel.ud5_products.network

import com.morodaniel.ud5_products.network.services.ProductsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {
    private const val URLBase: String = "http://192.168.1.50:3000/"

    private val logging = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(this.URLBase)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Services
    val productsService: ProductsService = retrofit.create(ProductsService::class.java)
}