package com.sum.shop.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentHomeBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClickAddClothes.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_addProductFragment)
        }

        binding.tvSeeAll.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_productDetailFragment)

        }
        binding.cvWoman.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_womanFragment)

        }
        binding.cvMan.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_manFragment)

        }
        binding.cvAccessory.setOnClickListener {
            Navigation.sent(it, R.id.action_homeFragment_to_homeAppliancesFragment)

        }
    }
}