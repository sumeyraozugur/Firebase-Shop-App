package com.sum.shop.ui.loginregister.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firebaseRepo: FirebaseAuthRepository) :
    ViewModel() {

    val isSingnIn: LiveData<Boolean> = firebaseRepo.isSignIn

    fun signIn(email: String, password: String) = firebaseRepo.signIn(email, password)
}