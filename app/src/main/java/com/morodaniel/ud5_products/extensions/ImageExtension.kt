package com.morodaniel.ud5_products.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.imageUrl(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this)
}