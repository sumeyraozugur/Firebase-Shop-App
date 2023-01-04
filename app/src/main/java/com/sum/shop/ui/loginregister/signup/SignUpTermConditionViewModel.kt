package com.sum.shop.ui.loginregister.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseAuthRepository

class SignUpTermConditionViewModel : ViewModel() {
    private val firebaseRepo = FirebaseAuthRepository()

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    var resultOk = MutableLiveData<Boolean>()


    init {
        _isSuccess = firebaseRepo.isSuccess
        resultOk = firebaseRepo.resultOk
    }


    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean
    ) = firebaseRepo.signUp(firstName, lastName, eMail, password, isAccept)

      fun checkResult(){
          firebaseRepo.checkResult()
      }



}