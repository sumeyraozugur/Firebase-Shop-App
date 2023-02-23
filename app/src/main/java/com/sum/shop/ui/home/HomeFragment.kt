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
import com.sum.shop.utils.sent

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val categoryAdapter by lazy { CategoryAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAddStyle.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_addProductFragment)
        }

        val categoryList = arrayListOf<CategoryModel>()
        val woman = CategoryModel(
            "Woman",
            R.drawable.woman2
        )
        val man = CategoryModel(
            "Man",
            R.drawable.man
        )
        val child = CategoryModel(
            "Child",
            R.drawable.child
        )
        categoryList.add(woman)
        categoryList.add(man)
        categoryList.add(child)
        categoryAdapter.setData(categoryList)
        binding.rvCategory.adapter =categoryAdapter
        binding.rvCategory.set3DItem(true)
        binding.rvCategory.setAlpha(true)

   /*     categoryAdapter.categoryClick={
            when(it){
                0 -> findNavController().navigate(
                    R.id.action_homeFragment_to_womanFragment)
                1-> findNavController().navigate(
                    R.id.action_homeFragment_to_manFragment)
                else->
                    findNavController().navigate(
                        R.id.action_homeFragment_to_childrenFragment)
            }
        }*/

    }
}