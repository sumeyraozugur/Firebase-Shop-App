package com.sum.shop.ui.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentPasswordForgotBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.back
import com.sum.shop.utils.extension.isValidEmail
import com.sum.shop.utils.extension.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(R.layout.fragment_password_forgot) {
    private val binding by viewBinding(FragmentPasswordForgotBinding::bind)
    private val viewModel: ForgotPasswordViewModel by viewModels() // dont use with by lazy

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChangePassword()


        with(binding) {
            profileToolbar.ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
            profileToolbar.tvProductTitle.text = getString(R.string.password)


            btnSubmit.setOnClickListener {
                val emailResult = etEmail.isValidEmail(getString(R.string.invalid_mail))
                if (emailResult) {
                    val email = etEmail.text.toString()
                    viewModel.changePassword(email)
                }
            }
        }
    }

    private fun observeChangePassword() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                requireView().showErrorSnackBar(getString(R.string.email_sent), false)
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            } else {
                requireView().showErrorSnackBar(getString(R.string.fail), true)
            }
        }
    }
}