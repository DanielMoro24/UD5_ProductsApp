package com.morodaniel.ud5_products.network.models


import com.google.gson.annotations.Expose

data class ProductsAPIResponse(
    @Expose
    val prods: List<Prod>,
    @Expose
    val resp: String
)