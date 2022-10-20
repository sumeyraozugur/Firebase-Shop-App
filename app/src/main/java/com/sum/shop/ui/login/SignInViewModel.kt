package com.sum.shop.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignInViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSignIn= MutableLiveData<Boolean>()
    val isSignIn: MutableLiveData<Boolean>
        get()=_isSignIn

    private var _isCurrentUser= MutableLiveData<Boolean>()
    val isCurrentUser: MutableLiveData<Boolean>
        get()=_isCurrentUser



    fun signIn(email:String,password:String){
        firebaseRepo.signIn(email,password)
        _isSignIn = firebaseRepo.isSignIn
    }

    fun checkCurrentUser(){
        firebaseRepo.checkCurrentUser()
        _isCurrentUser=firebaseRepo.isCurrentUser
    }

}