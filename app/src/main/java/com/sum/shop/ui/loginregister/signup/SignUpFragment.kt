package com.sum.shop.ui.loginregister.signup

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sum.shop.utils.constant.Constant
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSignUpBinding
import com.sum.shop.delegate.viewBinding

import com.sum.shop.utils.showErrorSnackBar

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private lateinit var viewModel : SignUpTermConditionViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SignUpTermConditionViewModel::class.java] //live data for update
        initObservers()

        binding.tvTermsCondition.setOnClickListener {
           viewModel.navigateToTermCondition(it)
        }


        with(binding) {
            btnRegister.setOnClickListener {
                val firstName = etFirstName.text.toString().trim { it <= ' ' }
                val lastName = etLastName.text.toString().trim { it <= ' ' }
                val email = etEmail.text.toString().trim { it <= ' ' }
                val password = etPassword.text.toString().trim { it <= ' ' }
                val isCheck = cbTermsAndCondition.isChecked
                val picture = Constant.PROFILE_PICTURE_LINK.toUri()

                if (validateSignUpDetails()) {
                    viewModel.signUp(firstName, lastName, email, password,picture ,isCheck)
                }
            }
        }
    }



    private fun validateSignUpDetails(): Boolean {
        with(binding){
            val firstName = binding.etFirstName.text.toString().trim { it <= ' ' }
            val lastName = binding.etLastName.text.toString().trim { it <= ' ' }
            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }
            val confirmPassword = binding.etConfirmPassword.text.toString().trim { it <= ' ' }
            val isCheck = binding.cbTermsAndCondition.isChecked


        return when {
            firstName.isEmpty() -> showError(getString(R.string.required_name))
            lastName.isEmpty() -> showError(getString(R.string.required_lastname))
            email.isEmpty() -> showError(getString(R.string.required_email))
            password.isEmpty() ->showError(getString(R.string.required_password))
            confirmPassword.isEmpty() ->showError(getString(R.string.required_password))
            password != confirmPassword ->showError(getString(R.string.password_match))
            !isCheck -> showError(getString(R.string.accept_condition))
            else -> true

        }
        }
    }
    private fun showError(errorMsg: String): Boolean {
        requireView().showErrorSnackBar(errorMsg, true)
        return false
    }

    private fun initObservers() {
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireView().showErrorSnackBar( getString(R.string.success),false)

            } else {
                requireView().showErrorSnackBar( getString(R.string.fail),true)
            }
        })

        viewModel.resultOk.observe(viewLifecycleOwner, Observer {
            binding.cbTermsAndCondition.isChecked = it
        })
    }
}