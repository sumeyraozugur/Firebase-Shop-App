package com.sum.shop.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sum.shop.MainActivity
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProfileBinding
import com.sum.shop.delegate.viewBinding
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
            findNavController().navigate(R.id.loginRegiser)
        }


     with(binding){
         viewModel.profileInfo.observe(viewLifecycleOwner) {
             tvProfileName.text = "${it.firstName} ${it.lastName}"
             tvProfileEmail.text = it.email
             Glide.with(binding.ivProfile).load(it.picture).into(binding.ivProfile)

         }
     }
    }
}