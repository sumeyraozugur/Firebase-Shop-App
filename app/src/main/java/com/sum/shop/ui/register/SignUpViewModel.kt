package com.sum.shop.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignUpViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSignUp= MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean>
        get()=_isSignUp

    init {
        _isSignUp = firebaseRepo.isSignUp
    }


    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean
    ) = firebaseRepo.signUp(firstName,lastName,eMail,password,isAccept)

}