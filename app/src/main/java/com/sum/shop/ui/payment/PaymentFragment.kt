package com.sum.shop.ui.payment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentPaymentBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.basket.BasketFragmentDirections
import com.sum.shop.utils.back
import com.sum.shop.utils.sent
import com.sum.shop.utils.showErrorSnackBar


class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val binding by viewBinding(FragmentPaymentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PaymentFragmentArgs by navArgs()
        val totalAmount = args.objectTotalAmount


        with(binding){
            //Back
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }

            btnPay.setOnClickListener {
                if(checkInfo()){
                    val action = PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment(totalAmount)
                    Navigation.findNavController(it).navigate(action)
                }
            }
            }

        }

    private fun checkInfo(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etCardName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_card_name),
                    true
                )
                false
            }
            TextUtils.isEmpty(binding.etCardNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_card_number),
                    true
                )
                false
            }
            TextUtils.isEmpty(binding.etExpiryDate.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_expiry),
                    true
                )
                false
            }
            TextUtils.isEmpty(binding.etCvv.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_cvv),
                    true
                )
                false
            }
            TextUtils.isEmpty(binding.etAddress.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_address),
                    true
                )
                false
            }

            else -> {
                true
            }
        }
    }

    }


