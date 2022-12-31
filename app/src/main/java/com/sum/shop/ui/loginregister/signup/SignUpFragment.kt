package com.sum.shop.ui.loginregister.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.sum.shop.Constant
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSignUpBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent
import com.sum.shop.utils.showErrorSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by lazy { SignUpViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIsSignUp()
        checkCondition()


        binding.tvTermsCondition.setOnClickListener {
            Navigation.sent(it, R.id.action_loginRegiser_to_termConditionBottomSheet)
        }


        with(binding) {
            btnRegister.setOnClickListener {
                val firstName = etFirstName.text.toString().trim { it <= ' ' }
                val lastName = etLastName.text.toString().trim { it <= ' ' }
                val email = etEmail.text.toString().trim { it <= ' ' }
                val password = etPassword.text.toString().trim { it <= ' ' }
                val isCheck = cbTermsAndCondition.isChecked

                if (validateSignUpDetails()) {
                    viewModel.signUp(firstName, lastName, email, password, isCheck)
                }
            }
        }
    }


    private fun validateSignUpDetails(): Boolean {
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
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_password),
                    true
                )
                false
            }
            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_password),
                    true
                )
                false
            }

            binding.etPassword.text.toString()
                .trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.password_match),
                    true
                )
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.accept_condition),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun observeIsSignUp() {
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.success),
                    false
                )
            } else {
                showErrorSnackBar(requireContext(), requireView(), getString(R.string.fail), true)
            }
        })
    }

    //check it is clicked or not
    private fun checkCondition() {
        CoroutineScope(Dispatchers.Main).launch{
            if (Constant.result) {
                binding.cbTermsAndCondition.isChecked = true

        }

        }

    }
}