package com.sum.shop.ui.profile


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.FragmentProfileBinding
import com.sum.shop.delegate.viewBinding



class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by lazy{ProfileViewModel()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {


            initializeObserver()

            tvEdit.setOnClickListener {
                viewModel.navigateToUpdate(it)
            }

            btnLogout.setOnClickListener {
                viewModel.signOut()
                findNavController().navigate(R.id.loginRegiser)
            }



            viewModel.profileInfo.observe(viewLifecycleOwner) {
                tvProfileName.text = "${it.firstName} ${it.lastName}"
                tvProfileEmail.text = it.email
                Glide.with(binding.ivProfile).load(it.picture).into(binding.ivProfile)
            }
        }
    }

    private fun initializeObserver() {
        with(binding){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer{isLoading ->
            ivProfile.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvEdit.visibility =  if (isLoading) View.GONE else View.VISIBLE
            ivProfileBorder.visibility=  if (isLoading) View.GONE else View.VISIBLE
            tvProfileEmail.visibility=  if (isLoading) View.GONE else View.VISIBLE
            btnLogout.visibility=  if (isLoading) View.GONE else View.VISIBLE
            loadingLottie.visibility=  if (isLoading) View.VISIBLE else View.GONE
        })
    }
    }
}