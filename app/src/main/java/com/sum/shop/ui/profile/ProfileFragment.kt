package com.sum.shop.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            initializeObserver()

            tvEdit.setOnClickListener {
                Navigation.sent(it, R.id.action_profileFragment_to_updateProfileFragment)
            }

            btnLogout.setOnClickListener {
                viewModel.signOut()
                Navigation.sent(it, R.id.loginRegiser)
            }
        }
    }

    private fun initializeObserver() {
        with(binding) {

            viewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    setViewsGone(ivProfile, tvEdit, ivProfileBorder, tvProfileEmail, btnLogout)
                    loadingLottie.visible()
                } else {
                    setViewsVisible(ivProfile, tvEdit, ivProfileBorder, tvProfileEmail, btnLogout)
                    loadingLottie.gone()
                }
            }

            viewModel.profileInfo.observe(viewLifecycleOwner) {
                tvProfileName.text = "${it.firstName} ${it.lastName}"
                tvProfileEmail.text = it.email
                Glide.with(binding.ivProfile).load(it.picture).into(binding.ivProfile)
            }
        }
    }
}