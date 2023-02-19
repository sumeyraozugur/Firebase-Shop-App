package com.sum.shop.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProductsBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.favorite.FavoriteViewModel
import com.sum.shop.utils.back

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val productAdapter by lazy { ProductsAdapter() }
    private lateinit var viewModel: ProductsViewModel
    val args: ProductsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        val categoryType = args.category
        viewModel.getProduct(categoryType.lowercase())
        initObservers()

        with(binding) {
            tvProductTitle.text = categoryType
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
            rvProduct.adapter = productAdapter
        }
    }

    private fun initObservers() {
        with(binding) {
            viewModel.categoryList.observe(viewLifecycleOwner) {
                println(it)
                productAdapter.setData(it)
            }
        }
    }
}