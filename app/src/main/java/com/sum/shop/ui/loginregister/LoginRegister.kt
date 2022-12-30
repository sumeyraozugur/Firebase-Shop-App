package com.sum.shop.ui.loginregister


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.tabs.TabLayoutMediator
import com.sum.shop.Constant
import com.sum.shop.R
import com.sum.shop.databinding.FragmentLoginRegiserBinding
import com.sum.shop.delegate.viewBinding


class LoginRegister : Fragment(R.layout.fragment_login_regiser) {
    private val binding by viewBinding(FragmentLoginRegiserBinding::bind)
    val titleList = arrayListOf(Constant.SIGN_IN, Constant.SIGN_UP)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCondition()


        binding.viewPager.adapter = TabAdapter(this)

        TabLayoutMediator(binding.loginTabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }


    private fun checkCondition() {
        setFragmentResultListener("checkBox") { _, bundle ->
            Constant.result = bundle.getBoolean("acceptCondition")//true
           // activity?.recreate()


            /*     val fragment: SignUpFragment? =
                     childFragmentManager.findFragmentById(R.id.action_loginRegiser_to_signUpFragment) as SignUpFragment?
                 val intent = requireActivity().intent
                 fragment?.startActivity(intent)
                 setFragmentResult("check", bundleOf("accept" to result))*/


        }
    }


}