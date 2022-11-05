package com.sum.shop.ui.updateprofile

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentUpdateProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.profile.ProfileViewModel
import com.sum.shop.utils.showErrorSnackBar


class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {

    private val binding by viewBinding(FragmentUpdateProfileBinding::bind)
    private val viewModel by lazy{ UpdateProfileViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        with(binding) {
            btnUpdate.setOnClickListener {
                val firstName = etFirstName.text.toString().trim { it <= ' ' }
                val lastName = etLastName.text.toString().trim { it <= ' ' }
                val email = etEmail.text.toString().trim { it <= ' ' }

                if(validateProfile())
                    viewModel.updateProfile(firstName, lastName, email)
            }
        }
    }
    private fun validateProfile(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_lastname),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_email),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun initObservers(){
        with(binding){
            viewModel.profileInfo.observe(viewLifecycleOwner) {
                etFirstName.setText(it.firstName)
                etLastName.setText( it.lastName)
                etEmail.setText(it.email)
            }

            viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
                if (it) {
                    //context?.showToast("Success")
                    showErrorSnackBar(requireContext(), requireView(), getString(R.string.success), false)
                } else {
                    //  context?.showToast(getString(R.string.fail))
                    showErrorSnackBar(requireContext(), requireView(), getString(R.string.fail), true)
                }
            })

        }
    }

}