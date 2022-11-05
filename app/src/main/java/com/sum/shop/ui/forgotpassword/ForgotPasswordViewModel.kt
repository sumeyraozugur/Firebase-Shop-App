package com.sum.shop.ui.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class ForgotPasswordViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSuccess= MutableLiveData<Boolean>()
    val isSuccess:MutableLiveData<Boolean>
        get()=_isSuccess

   init {
       _isSuccess = firebaseRepo.isSuccess
   }

    fun changePassword(email:String) =firebaseRepo.changePassword(email)

}