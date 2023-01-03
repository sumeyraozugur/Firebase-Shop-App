package com.sum.shop.ui.children

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sum.shop.R
import com.sum.shop.databinding.FragmentChildrenBinding
import com.sum.shop.delegate.viewBinding


class ChildrenFragment : Fragment(R.layout.fragment_children) {
    private val binding by viewBinding(FragmentChildrenBinding::bind)
    private val viewModel by lazy { ChildrenViewModel() }
    private val childrenAdapter by lazy { ChildrenAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            rvChildren.adapter = childrenAdapter
        }

        viewModel.getProductAppliance()
        initObservers()

    }

    private fun initObservers() {
        with(binding) {
            viewModel.childrenList.observe(viewLifecycleOwner) {
                childrenAdapter.setData(it)
            }
        }
    }


}