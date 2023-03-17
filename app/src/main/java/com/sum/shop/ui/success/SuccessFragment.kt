package com.sum.shop.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSuccessBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.sent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment(R.layout.fragment_success) {
    private val binding by viewBinding(FragmentSuccessBinding::bind)
    private val viewModel: SuccessViewModel by viewModels()
    private val args: SuccessFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAmount = args.objectTotalAmount

        initializeObserver()

        with(binding) {
            btnBackShop.setOnClickListener {
                Navigation.sent(it, R.id.action_successFragment_to_homeFragment)
            }
            tvSuccessInformation.text = getString(R.string.success_payment, totalAmount.toString())
        }
        // Proguard
    }


    private fun initializeObserver() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            basketList.forEach { basketModel ->
                viewModel.deleteFromBasket(basketModel.uuid)
            }
        }
    }
}