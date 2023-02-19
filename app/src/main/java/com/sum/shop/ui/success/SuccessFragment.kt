package com.sum.shop.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSuccessBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.basket.BasketViewModel
import com.sum.shop.ui.payment.PaymentFragmentArgs
import com.sum.shop.utils.sent

class SuccessFragment : Fragment(R.layout.fragment_success) {
    private val binding by viewBinding(FragmentSuccessBinding::bind)
    private lateinit var viewModel: SuccessViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SuccessViewModel::class.java]
        val args: SuccessFragmentArgs by navArgs()
        val totalAmount = args.objectTotalAmount

        initObserver()

        with(binding){

            btnBackShop.setOnClickListener {
                Navigation.sent(it,R.id.action_successFragment_to_homeFragment)
            }
            tvSuccessInformation.text ="Thank you! Your payment of ${totalAmount}Tl has been received"

        }


    }


    private fun initObserver(){
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            basketList.forEach{basketModel->
                viewModel.deleteFromBasket(basketModel.uuid)

            }

        }
    }


}