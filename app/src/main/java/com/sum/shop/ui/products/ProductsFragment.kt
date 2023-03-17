package com.sum.shop.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProductsBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.model.ProductModel
import com.sum.shop.utils.extension.back
import com.sum.shop.utils.extension.gone
import com.sum.shop.utils.extension.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val productAdapter by lazy { ProductsAdapter(onClickDetail = ::onClickDetail) }
    private val viewModel: ProductsViewModel by viewModels()
    private val args: ProductsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryType = args.category
        viewModel.getProduct(categoryType.lowercase())
        initObservers()

        with(binding) {

            productToolbar.apply {
                tvProductTitle.text = categoryType
                ibArrowBack.setOnClickListener {
                    Navigation.back(it)
                }
            }
            rvProduct.adapter = productAdapter
        }
    }

    private fun onClickDetail(productModel: ProductModel) {
        val action =
            ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                productModel
            )
        findNavController().navigate(action)

    }

    private fun initObservers() {
        with(binding) {
            viewModel.categoryList.observe(viewLifecycleOwner) { productList ->
                productAdapter.setData(productList)
            }

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    rvProduct.gone()
                    loadingLottie.visible()
                } else {
                    rvProduct.visible()
                    loadingLottie.gone()
                }
            }
        }
    }
}