package com.sum.shop.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSuccessBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent

class SuccessFragment : Fragment(R.layout.fragment_success) {
    private val binding by viewBinding(FragmentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackShop.setOnClickListener {
            Navigation.sent(it,R.id.action_successFragment_to_homeFragment)
        }


    }


}