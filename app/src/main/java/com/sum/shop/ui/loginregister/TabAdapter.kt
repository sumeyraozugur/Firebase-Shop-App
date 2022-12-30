package com.sum.shop.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sum.shop.ui.login.signin.SignInFragment
import com.sum.shop.ui.login.signup.SignUpFragment

class TabAdapter(fragment: Fragment):
    FragmentStateAdapter(fragment){


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SignInFragment()
            1 -> return SignUpFragment()
        }
        return SignInFragment()

    }
}