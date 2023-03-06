package com.sum.shop.ui.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val firebaseRepo:FirebaseAuthRepository) : ViewModel() {
   // private val firebaseRepo = FirebaseAuthRepository()

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: MutableLiveData<Boolean>
        get() = _isSuccess

    init {
        _isSuccess = firebaseRepo.isSuccess
    }

    fun changePassword(email: String) = firebaseRepo.changePassword(email)

}