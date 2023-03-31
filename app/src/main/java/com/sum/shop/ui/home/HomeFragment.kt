package com.sum.shop.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentHomeBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.model.CategoryModel
import com.sum.shop.utils.extension.sent

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val categoryAdapter by lazy { CategoryAdapter(onClickCategory = ::onClickCategory) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAddStyle.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_addProductFragment)
        }

        val categoryList = arrayListOf<CategoryModel>()
        val woman = CategoryModel(
            getString(R.string.woman),
            R.drawable.woman2
        )
        val man = CategoryModel(
            getString(R.string.man),
            R.drawable.man
        )
        val child = CategoryModel(
            getString(R.string.child),
            R.drawable.child
        )
        categoryList.add(woman)
        categoryList.add(man)
        categoryList.add(child)
        categoryAdapter.submitList(categoryList)
        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.set3DItem(true)
        binding.rvCategory.setAlpha(true)
    }

    private fun onClickCategory(categoryModel: CategoryModel) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToProductsFragment(categoryModel.categoryName)
        findNavController().navigate(action)
    }
}