package com.sum.shop.ui.woman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.sum.shop.R
import com.sum.shop.databinding.FragmentWomanBinding
import com.sum.shop.delegate.viewBinding


class WomanFragment : Fragment(R.layout.fragment_woman) {
    private val binding by viewBinding(FragmentWomanBinding::bind)
    private val womanAdapter by lazy { WomanAdapter() }
    private val viewModel by lazy { WomanViewModel() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            rvWoman.adapter = womanAdapter
        }

        viewModel.getProductWoman()
        initObservers()

    }

    private fun initObservers() {
        with(binding) {
            viewModel.womanList.observe(viewLifecycleOwner) {
                womanAdapter.setData(it)
            }
        }
    }

}