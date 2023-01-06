package com.sum.shop.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProductsBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.back

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val productAdapter by lazy { ProductsAdapter() }
    private val viewModel by lazy { ProductsViewModel() }
    val args: ProductsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProduct(args.category.lowercase())
        initObservers()


        with(binding) {
            tvProductTitle.text = args.category

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