package com.morodaniel.ud5_products.ui

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.morodaniel.ud5_products.databinding.ItemProductBinding
import com.morodaniel.ud5_products.extensions.imageUrl
import com.morodaniel.ud5_products.model.Product

class ProductListAdapter(private val onProductClick: (Product) -> Unit) :
    ListAdapter<Product, ProductListAdapter.ViewHolder>(TechnologyItemCallback()) {

    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.binding.tvName.text = product.name
        holder.binding.tvPrice.text = product.discountPrice.toString() + " €"
        holder.binding.ivImage.imageUrl(product.image)
        holder.binding.root.setOnClickListener { onProductClick(product) }
        if(product.amount > 5){
            holder.binding.tvAmountWarning.visibility = View.GONE
        }
    }

}


class TechnologyItemCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.name == newItem.name && oldItem.description == newItem.description
}
