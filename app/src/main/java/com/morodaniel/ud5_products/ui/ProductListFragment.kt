package com.morodaniel.ud5_products.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.morodaniel.ud5_products.databinding.FragmentProductListBinding
import com.morodaniel.ud5_products.network.NetworkConfig
import com.morodaniel.ud5_products.network.models.ProductsAPIResponse
import com.morodaniel.ud5_products.network.models.toMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val adapter = ProductListAdapter {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(it.id)
        findNavController().navigate(action)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProducts.layoutManager = GridLayoutManager(context, 2)
        binding.rvProducts.adapter = adapter

        getProducts()
    }

    private fun getProducts() {
        NetworkConfig.productsService.getProducts().enqueue(object : Callback<ProductsAPIResponse> {
            override fun onResponse(
                call: Call<ProductsAPIResponse>,
                response: Response<ProductsAPIResponse>
            ) {
                if (response.isSuccessful) {
                    val prods = response.body()
                    adapter.submitList(prods?.prods.toMap())
                } else {
                    Log.e("Network", "error en la conexion")
                }
            }

            override fun onFailure(call: Call<ProductsAPIResponse>, t: Throwable) {
                Log.e("Network", "error en la conexion", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}