package com.morodaniel.ud5_products.network.models


import com.google.gson.annotations.Expose
import com.morodaniel.ud5_products.model.Product

data class ProductsAPIResponse(
    @Expose
    val prods: List<ProdAPI>,
    @Expose
    val resp: String
)

data class ProdAPI(
    @Expose
    val amount: Int,
    @Expose
    val availability: Boolean,
    @Expose
    val description: String,
    @Expose
    val discountPrice: Int,
    @Expose
    val id: Int,
    @Expose
    val image: String,
    @Expose
    val name: String,
    @Expose
    val price: Int
)

fun ProdAPI.toProduct(): Product {
    return Product(amount, availability, description, discountPrice, id, image, name, price)
}

fun List<ProdAPI>?.toMap(): List<Product> {
    return this?.map { it.toProduct() } ?: emptyList()
}