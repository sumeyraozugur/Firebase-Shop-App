package com.sum.shop.ui.loginregister.signin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.repository.FirebaseAuthRepository
import com.sum.shop.utils.sent

class SignInViewModel : ViewModel() {
    private val firebaseRepo = FirebaseAuthRepository()

    private var _isSignIn= MutableLiveData<Boolean>()
    val isSingnIn: LiveData<Boolean>
        get()=_isSignIn

    private var _isSuccess= MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get()=_isSuccess


    init {
        _isSignIn = firebaseRepo.isSignIn
        _isSuccess = firebaseRepo.isSuccess
    }

   fun navigateToForgot(view:View)= Navigation.sent(view, R.id.action_loginRegiser2_to_forgotPasswordFragment)

    fun signIn(email:String,password:String) = firebaseRepo.signIn(email,password)
}