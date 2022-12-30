package com.sum.shop.ui.loginregister.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignUpViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSuccess= MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get()=_isSuccess

    init {
        _isSuccess = firebaseRepo.isSuccess
    }


    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean
    ) = firebaseRepo.signUp(firstName,lastName,eMail,password,isAccept)

}