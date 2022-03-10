package com.morodaniel.ud5_products.network.services

import com.morodaniel.ud5_products.network.models.ProductsAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("getProds")
    fun getProducts(): Call<ProductsAPIResponse>

    @GET("getProdById/{id}")
    fun getOneProduct(@Path("id") id: Int): Call<ProductsAPIResponse>
}