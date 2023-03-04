package com.sum.shop.ui.loginregister.signup

import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.repository.FirebaseAuthRepository
import com.sum.shop.utils.sent

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
        picture:Uri,
        isAccept: Boolean
    ) = firebaseRepo.signUp(firstName, lastName, eMail, password, picture,isAccept)

      fun checkResult(){
          firebaseRepo.checkResult()
      }

    fun navigateToTermCondition(view:View){
        Navigation.sent(view, R.id.action_loginRegiser_to_termConditionBottomSheet)
    }
}