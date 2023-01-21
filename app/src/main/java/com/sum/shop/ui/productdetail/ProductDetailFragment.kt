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
import com.sum.shop.room.FavProductDatabase
import com.sum.shop.utils.back


class ProductDetailFragment() : Fragment(R.layout.fragment_product_detail) {
    private val binding by viewBinding(FragmentProductDetailBinding::bind)
    private val viewModel: ProductDetailViewModel by viewModels()
    val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var favoritesDatabase: FavProductDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.objectProduct
        favoritesDatabase = FavProductDatabase.getDatabase(requireContext())
        var favModel = FavModel( // room da kontrol et!!
            id,
            product.img,
            product.productTitle,
            product.productDescription,
            product.productPrice,
        )

        //initObserver()

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

            if (!favModel.isFav) {
                favModel.isFav = true
                //Update Current User
                val updateFavModel =
                    FavModel(
                        favModel.id, favModel.img, favModel.productTitle,
                        favModel.productDescription, favModel.productPrice, favModel.isFav
                    )
                viewModel.updateFav(updateFavModel)
                viewModel.addToFav(updateFavModel)
                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
            } else {
                favModel.isFav = false
                val updateFavModel =
                    FavModel(
                        favModel.id, favModel.img, favModel.productTitle,
                        favModel.productDescription, favModel.productPrice, favModel.isFav
                    )
                viewModel.updateFav(updateFavModel)
                viewModel.deleteFromFav(updateFavModel.id)
                Log.v("Delete Id", updateFavModel.img.toString())

                binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)

            }

            //println(favModel.isFav)

            //view?.let { Snackbar.make(it, R.string.this_product_added_fav, 1000).show() }
        }

        if (favModel.isFav) {
            binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_full_fav)
        } else {
            binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)
        }




        binding.btnAddToBasket.setOnClickListener {
            viewModel.addToBasket(
                BasketModel(
                    id,
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

/// var favoritesList = favoritesDatabase.favDao().readAllFav()

//       var favoritesList = favoritesDatabase.favDao().readAllFav()


// viewModel.addFav(favModel)

/*    if (favoritesList.contains(favModel)) {
        viewModel.deleteTodo(favModel.id)
        binding.btnDetailAddFav.setBackgroundResource(R.drawable.ic_favorite)

    } else {
        binding.btnDetailAddFav.apply {
            viewModel.addFav(favModel)

            setBackgroundResource(R.drawable.ic_full_fav)
        }
    }*/