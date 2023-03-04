package com.sum.shop.ui.success


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSuccessBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent

class SuccessFragment : Fragment(R.layout.fragment_success) {
    private val binding by viewBinding(FragmentSuccessBinding::bind)
    private val viewModel by lazy{ SuccessViewModel(requireActivity().application) }
    private val args:SuccessFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAmount = args.objectTotalAmount

        initializeObserver()

        with(binding){
            btnBackShop.setOnClickListener {
                viewModel.navigateHome(it)
            }
            tvSuccessInformation.text = getString(R.string.success_payment,totalAmount.toString())
        }
        // Proguard                   )
    }


    private fun initializeObserver(){
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            basketList.forEach{basketModel->
                viewModel.deleteFromBasket(basketModel.uuid)
            }
        }
    }
}