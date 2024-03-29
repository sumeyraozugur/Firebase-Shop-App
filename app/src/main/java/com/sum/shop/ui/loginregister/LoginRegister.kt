package com.sum.shop.ui.loginregister

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sum.shop.R
import com.sum.shop.databinding.FragmentLoginRegiserBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.constant.Constant


class LoginRegister : Fragment(R.layout.fragment_login_regiser) {
    private val binding by viewBinding(FragmentLoginRegiserBinding::bind)
    private val titleList = arrayListOf(Constant.SIGN_IN, Constant.SIGN_UP)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewPager.adapter = TabAdapter(this)

        TabLayoutMediator(binding.loginRegisterTabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }
}