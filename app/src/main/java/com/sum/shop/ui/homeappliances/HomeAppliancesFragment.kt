package com.sum.shop.ui.homeappliances

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sum.shop.R
import com.sum.shop.databinding.FragmentHomeAppliancesBinding
import com.sum.shop.delegate.viewBinding


class HomeAppliancesFragment : Fragment(R.layout.fragment_home_appliances) {
    private val binding by viewBinding(FragmentHomeAppliancesBinding::bind)
    private val viewModel by lazy { HomeAppliancesViewModel() }
    private val applianceAdapter by lazy { HomeApplianceAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding){
            rvAppliance.adapter = applianceAdapter
        }

        viewModel.getProductAppliance()
        initObservers()

    }

    private fun initObservers() {
        with(binding) {
            viewModel.applianceList.observe(viewLifecycleOwner) {
                applianceAdapter.setData(it)
            }
        }
    }



}