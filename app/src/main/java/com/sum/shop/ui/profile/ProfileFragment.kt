package com.sum.shop.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sum.shop.MainActivity
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.login.SignInFragment
import com.sum.shop.ui.register.SignUpFragment
import com.sum.shop.utils.sent


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by lazy{ProfileViewModel()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEdit.setOnClickListener {
            Navigation.sent(it, R.id.action_profileFragment_to_updateProfileFragment)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
            val intent= Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }

     with(binding){
         viewModel.profileInfo.observe(viewLifecycleOwner) {
             tvProfileName.text = "${it.firstName} ${it.lastName}"
             tvProfileEmail.text = it.email
         }
     }


    }
}