package com.sum.shop.ui.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class ForgotPasswordViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()


    private var _isChange= MutableLiveData<Boolean>()
    val isChange:MutableLiveData<Boolean>
        get()=_isChange

    fun changePassword(email:String){
        firebaseRepo.changePassword(email)
        _isChange = firebaseRepo.isChangePassword
    }
}