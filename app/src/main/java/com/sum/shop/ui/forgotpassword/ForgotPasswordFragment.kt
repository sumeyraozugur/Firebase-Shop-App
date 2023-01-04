package com.sum.shop.ui.forgotpassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentPasswordForgotBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.back
import com.sum.shop.utils.isValidEmail
import com.sum.shop.utils.showErrorSnackBar
import com.sum.shop.utils.showToast


class ForgotPasswordFragment : Fragment(R.layout.fragment_password_forgot) {
    private val binding by viewBinding(FragmentPasswordForgotBinding::bind)
    private val viewModel by lazy { ForgotPasswordViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChangePassword()


        with(binding) {
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }

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
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                showErrorSnackBar(requireContext(),requireView(),getString(R.string.email_sent),false)
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            } else {
                showErrorSnackBar(requireContext(),requireView(),getString(R.string.fail),true)
            }
        })
    }
}