package com.sum.shop.ui.loginregister.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpTermConditionViewModel @Inject constructor(private val firebaseRepo: FirebaseAuthRepository) :
    ViewModel() {

    private var _isSuccess = firebaseRepo.isSuccess
    val isSuccess: LiveData<Boolean> = _isSuccess

    val resultOk = MutableLiveData<Boolean>()


    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        picture: Uri,
        isAccept: Boolean
    ) = firebaseRepo.signUp(firstName, lastName, eMail, password, picture)

    //check radio button clicked or not
    fun checkResult() {
        resultOk.value = true
    }
}