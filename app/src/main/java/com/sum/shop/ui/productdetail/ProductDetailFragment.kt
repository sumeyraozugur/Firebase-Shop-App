package com.sum.shop.ui.productdetail

import ProductDetailViewModel
import android.content.Intent
import android.os.Bundle
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
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.objectProduct
        val favModel = FavModel(
            uuid = product.id,
            img = product.img,
            productTitle = product.productTitle,
            productDescription = product.productDescription,
            productPrice = product.productPrice,
            productFav = product.productFav
        )

        shareLink()

        with(binding) {
            tvDetailName.text = product.productTitle
            tvDetailInfo.text = product.productDescription
            tvDetailPrice.text = "${product.productPrice} TL"

            Glide.with(this@ProductDetailFragment).load(product.img)
                .into(binding.ivProductImage)

            //Back
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }

            //Add fav products to room database
            btnDetailAddFav.setOnClickListener {
                if (product.productFav) {
                    btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)
                    product.productFav = false
                    viewModel.deleteFromFav(favModel)
                } else {
                    btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
                    product.productFav = true
                    viewModel.addToFav(favModel)
                }
            }


            if (product.productFav) {
                btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
            }
            else {
                btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)
            }


            btnAddToBasket.setOnClickListener {
                viewModel.addToBasket(
                    BasketModel(
                        id,
                        product.id,
                        product.img,
                        product.productTitle,
                        product.productDescription,
                        product.productPrice.toInt(),
                        product.productCount,
                        product.total_amount
                    )
                )
            }
        }
    }

    private fun shareLink(){
        binding.btnDetailShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, binding.webView.url)
                startActivity(Intent.createChooser(this,"Share Link"))
            }
        }
    }
}



