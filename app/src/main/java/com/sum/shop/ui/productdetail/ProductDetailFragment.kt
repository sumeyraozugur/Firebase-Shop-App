package com.sum.shop.ui.productdetail

import ProductDetailViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProductDetailBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.utils.back


class ProductDetailFragment() : Fragment(R.layout.fragment_product_detail) {
    private val binding by viewBinding(FragmentProductDetailBinding::bind)
    private val viewModel: ProductDetailViewModel by viewModels()
    val args: ProductDetailFragmentArgs by navArgs()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.objectProduct
        val favoritesList = viewModel.kisilerListesi
        val favModel = FavModel(
            // room da kontrol et!!
            uuid= product.id,
            img = product.img,
            productTitle = product.productTitle,
            productDescription = product.productDescription,
            productPrice = product.productPrice,
        )



        Glide.with(this).load(product.img)
            .into(binding.ivProductImage)
        with(binding) {
            tvDetailName.text = product.productTitle
            tvDetailInfo.text = product.productDescription
            tvDetailPrice.text = "${product.productPrice} TL"

            //Back
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
        }

        //Add fav products to room database
        binding.btnDetailAddFav.setOnClickListener {
            if (favoritesList.value?.contains(favModel) == true) {
                println("FavLis:${favoritesList.value}")
                binding.btnDetailAddFav.apply {
                    viewModel.deleteFromFav(favModel)
                    setBackgroundResource(R.drawable.ic_favorite)
                }
            } else {
                binding.btnDetailAddFav.apply {
                    viewModel.addToFav(favModel)
                    setBackgroundResource(R.drawable.ic_full_fav)
                }
            }
        }

        Log.v("Genel",favoritesList.value.toString())

   /*     favoritesList.value.let {
            println("Size: ${it?.size}")
            if (it?.contains(favModel) ==true) {
                binding.btnDetailAddFav.apply {
                    setBackgroundResource(R.drawable.ic_full_fav)
                }
            } else {
                binding.btnDetailAddFav.apply {
                    setBackgroundResource(R.drawable.ic_favorite)
                }
            }

        }*/


        binding.btnAddToBasket.setOnClickListener {
            viewModel.addToBasket(
                BasketModel(
                    id,
                    product.id,
                    product.img,
                    product.productTitle,
                    product.productDescription,
                    product.productPrice,
                    product.productCount
                )
            )

        }
    }



}



