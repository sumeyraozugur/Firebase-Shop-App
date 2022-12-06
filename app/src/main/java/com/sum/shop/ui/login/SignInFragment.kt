package com.sum.shop.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSignInBinding

import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.isNullorEmpty
import com.sum.shop.utils.isValidEmail
import com.sum.shop.utils.sent
import com.sum.shop.utils.showErrorSnackBar


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by lazy { SignInViewModel() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkCurrentUser()
        initObservers()


        with(binding) {
            tvRegister.setOnClickListener {
                Navigation.sent(it, R.id.action_loginFragment_to_registerFragment)
            }
            tvForgotPassword.setOnClickListener {
                Navigation.sent(it, R.id.action_loginFragment_to_forgotPasswordFragment)
            }

            btnLogin.setOnClickListener {

                val emailResult =
                    etEmail.isValidEmail(getString(R.string.invalid_mail))   //validation
                val passwordResult = etPassword.isNullorEmpty(getString(R.string.invalid_password))

                if (emailResult && passwordResult) {
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()

                    viewModel.signIn(email, password)
                }
            }
        }
    }


    private fun initObservers() {
        viewModel.isSingnIn.observe(viewLifecycleOwner, Observer {
            if (it) {
                //context?.showToast("Success")
                findNavController().navigate(R.id.action_loginFragment_to_main_graph)
            } else {
                //  context?.showToast(getString(R.string.fail))
                showErrorSnackBar(requireContext(), requireView(), getString(R.string.fail), true)
            }
        })

    }




}