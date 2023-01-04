package com.sum.shop.ui.man

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.ActivityMainBinding.bind
import com.sum.shop.databinding.FragmentManBinding
import com.sum.shop.databinding.FragmentWomanBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.woman.WomanAdapter
import com.sum.shop.ui.woman.WomanViewModel
import com.sum.shop.utils.back


class ManFragment : Fragment(R.layout.fragment_man) {
    private val binding by viewBinding(FragmentManBinding::bind)
    private val manAdapter by lazy { ManAdapter() }
    private val viewModel by lazy { ManViewModel() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        with(binding){

            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }

            rvMan.adapter = manAdapter
        }

        viewModel.getProductMan()


    }

    private fun initObservers() {
        with(binding) {
            viewModel.manList.observe(viewLifecycleOwner) {
                manAdapter.setData(it)
            }
        }
    }


}