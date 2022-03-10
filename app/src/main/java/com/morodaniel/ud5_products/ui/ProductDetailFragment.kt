package com.morodaniel.ud5_products.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.morodaniel.ud5_products.databinding.FragmentProductDetailBinding
import com.morodaniel.ud5_products.extensions.imageUrl
import com.morodaniel.ud5_products.network.NetworkConfig
import com.morodaniel.ud5_products.network.models.ProductsAPIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()

    private var productId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = args.prodId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProdById()
    }

    private fun getProdById() {
        NetworkConfig.productsService.getOneProduct(productId).enqueue(object :
            Callback<ProductsAPIResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProductsAPIResponse>,
                response: Response<ProductsAPIResponse>
            ) {
                if (response.isSuccessful) {
                    val prod = response.body()?.prods?.get(0)
                    if (prod != null) {
                        binding.ivImage2.imageUrl(prod.image)
                        binding.tvDetailName.text = prod.name
                        binding.tvDetailDescription.text = prod.description
                        binding.tvDetailPrice.text = prod.price.toString() + " €"
                        binding.tvDetailDiscountPrice.text = prod.discountPrice.toString() + " €"
                        binding.tvDetailAmount.text = prod.amount.toString()
                        if (prod.availability) {
                            binding.tvDetailAvailability.text = "Available"
                        } else {
                            binding.tvDetailAvailability.text = "Not available"
                        }
                        if (prod.discountPrice.equals(prod.price)) {
                            binding.tvDetailDiscountPrice.visibility = View.GONE
                            binding.tvDiscountPrice.visibility = View.GONE
                        }
                    }
                } else {
                    Log.e("Network", "connexion error")
                }
            }

            override fun onFailure(call: Call<ProductsAPIResponse>, t: Throwable) {
                Log.e("Network", "connexion error", t)


            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}