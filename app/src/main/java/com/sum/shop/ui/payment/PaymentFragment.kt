package com.sum.shop.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentPaymentBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.back
import com.sum.shop.utils.sent


class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val binding by viewBinding(FragmentPaymentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Back
        binding.ibArrowBack.setOnClickListener {
            Navigation.back(it)
        }

        binding.btnPay.setOnClickListener {
            Navigation.sent(it, R.id.action_paymentFragment_to_successFragment)

        }
    }


}