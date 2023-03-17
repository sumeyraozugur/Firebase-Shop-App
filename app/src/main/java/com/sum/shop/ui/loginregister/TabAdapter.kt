package com.sum.shop.ui.loginregister

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sum.shop.ui.loginregister.signin.SignInFragment
import com.sum.shop.ui.loginregister.signup.SignUpFragment

class TabAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SignInFragment()
            1 -> return SignUpFragment()
        }
        return SignInFragment()
    }
}