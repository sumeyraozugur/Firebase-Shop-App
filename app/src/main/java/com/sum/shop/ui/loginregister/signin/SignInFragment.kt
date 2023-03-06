package com.sum.shop.ui.loginregister.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSignInBinding

import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.isNullorEmpty
import com.sum.shop.utils.isValidEmail
import com.sum.shop.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by viewModels()
    //private val viewModel by lazy { SignInViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()


        with(binding) {

            tvForgotPassword.setOnClickListener {
               viewModel.navigateToForgot(it)
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
                findNavController().navigate(R.id.action_loginRegiser_to_main_graph)
            } else {
                requireView().showErrorSnackBar(getString(R.string.fail), true)
            }
        })

         viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
             if(it){
                 findNavController().navigate(R.id.action_loginRegiser_to_main_graph)
             }
         })
    }
}