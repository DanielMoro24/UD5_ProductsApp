package com.morodaniel.ud5_products.model

data class Product(
    val amount: Int,

    val availability: Boolean,

    val description: String,

    val discountPrice: Int,

    val id: Int,

    val image: String,

    val name: String,

    val price: Int
)