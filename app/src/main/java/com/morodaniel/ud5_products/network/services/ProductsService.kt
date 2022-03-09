package com.morodaniel.ud5_products.network.services

import com.morodaniel.ud5_products.network.models.ProductsAPIResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductsService {

    @GET("getProds")
    fun getProducts(): Call<ProductsAPIResponse>
}