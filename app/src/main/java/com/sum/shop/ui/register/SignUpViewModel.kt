package com.sum.shop.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignUpViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSignUp= MutableLiveData<Boolean>()
    val isSignUp: MutableLiveData<Boolean>
        get()=_isSignUp


    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean
    ) {
        firebaseRepo.signUp(firstName,lastName,eMail,password,isAccept)
        _isSignUp = firebaseRepo.isSignUp

    }
}