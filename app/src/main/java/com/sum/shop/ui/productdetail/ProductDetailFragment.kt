package com.sum.shop.ui.productdetail

import ProductDetailViewModel
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
    val args: ProductDetailFragmentArgs by navArgs()
    //  var favModel = FavModel(1,"","","","")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.objectProduct

       // initObserver()


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
            var favModel = FavModel(
                0,
                product.img,
                product.productTitle,
                product.productDescription,
                product.productPrice,
            )
            viewModel.addFav(favModel)

          /*  if (!favModel.isFav) {
                favModel.isFav = true
                //Update Current User
                val updateFavModel =
                    FavModel(
                        favModel.id, favModel.img, favModel.productTitle,
                        favModel.productDescription, favModel.productPrice, favModel.isFav
                    )
                viewModel.updateToFav(updateFavModel)
                viewModel.addFav(favModel)
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
            } else {
                favModel.isFav = false
                val updateFavModel =
                    FavModel(
                        favModel.id, favModel.img, favModel.productTitle,
                        favModel.productDescription, favModel.productPrice, favModel.isFav
                    )
                viewModel.updateToFav(updateFavModel)
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)

            }
            if (favModel.isFav) {
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
            } else {
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)
            }
            println(favModel.isFav)
*/

            //view?.let { Snackbar.make(it, R.string.this_product_added_fav, 1000).show() }
        }




        binding.btnAddToBasket.setOnClickListener {
            viewModel.addFav(
                BasketModel(
                    0,
                    product.img,
                    product.productTitle,
                    product.productDescription,
                    product.productPrice,
                    product.productCount
                )
            )

        }

    }

/*    fun initObserver() {
        viewModel.isFav.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
            } else {
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)
            }

        }
    }*/


}