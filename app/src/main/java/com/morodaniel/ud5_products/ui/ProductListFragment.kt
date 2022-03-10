package com.morodaniel.ud5_products.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.morodaniel.ud5_products.databinding.FragmentProductListBinding
import com.morodaniel.ud5_products.network.NetworkConfig
import com.morodaniel.ud5_products.network.models.ProdAPI
import com.morodaniel.ud5_products.network.models.ProductsAPIResponse
import com.morodaniel.ud5_products.network.models.toMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {
    private var prods: List<ProdAPI>? = null
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

        binding.btnSearch.visibility = View.GONE
        binding.titSearch.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase()
            val usersFiltered = filterUsersByQuery(query)
            adapter.submitList(usersFiltered.toMap())
        }
        binding.titSearch.setOnEditorActionListener { textView, i, keyEvent ->
            hideKeyboard()
            true
        }
    }

    @SuppressLint("ServiceCast")
    private fun hideKeyboard() {
        binding.rvProducts.requestFocus()
        with(binding.rvProducts) {
            requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    private fun filterUsersByQuery(query: String) = prods?.filter { prod ->
        prod.name.lowercase().contains(query.lowercase())
    }

    private fun getProducts() {
        NetworkConfig.productsService.getProducts().enqueue(object : Callback<ProductsAPIResponse> {
            override fun onResponse(
                call: Call<ProductsAPIResponse>,
                response: Response<ProductsAPIResponse>
            ) {
                if (response.isSuccessful) {
                    prods = response.body()?.prods
                    adapter.submitList(prods?.toMap())
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