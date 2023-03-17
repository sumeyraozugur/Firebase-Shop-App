package com.sum.shop.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val firebaseRepo: FirebaseAuthRepository) :
    ViewModel() {

    private val _isSuccess = firebaseRepo.isSuccess
    val isSuccess: LiveData<Boolean> = _isSuccess


    fun changePassword(email: String) = firebaseRepo.changePassword(email)

}