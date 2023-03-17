package com.sum.shop.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentPaymentBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.back
import com.sum.shop.utils.extension.showErrorSnackBar
import com.sum.shop.utils.extension.trimmedText

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val args: PaymentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAmount = args.objectTotalAmount

        with(binding) {
            //Back
            productToolbar.ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
            productToolbar.tvProductTitle.text = getString(R.string.payment)

            btnPay.setOnClickListener {
                if (validatePaymentDetails()) {
                    val action =
                        PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment(totalAmount)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    private fun validatePaymentDetails(): Boolean {
        with(binding) {
            val cardName = etCardName.trimmedText()
            val cardNumber = etCardNumber.trimmedText()
            val expiryDate = etExpiryDate.trimmedText()
            val cvv = etCvv.trimmedText()
            val address = etAddress.trimmedText()


            return when {
                cardName.isEmpty() -> showError(getString(R.string.required_card_name))
                cardNumber.isEmpty() -> showError(getString(R.string.required_card_number))
                expiryDate.isEmpty() -> showError(getString(R.string.required_expiry))
                cvv.isEmpty() -> showError(getString(R.string.required_cvv))
                address.isEmpty() -> showError(getString(R.string.required_address))
                else -> true
            }
        }
    }

    private fun showError(errorMsg: String): Boolean {
        requireView().showErrorSnackBar(errorMsg, true)
        return false
    }
}
