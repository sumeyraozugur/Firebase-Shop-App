package com.sum.shop.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignInViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSignIn= MutableLiveData<Boolean>()
    val isSignIn: LiveData<Boolean>
        get()=_isSignIn

    private var _isCurrentUser= MutableLiveData<Boolean>()
    val isCurrentUser: LiveData<Boolean>
        get()=_isCurrentUser

    init {
        _isSignIn = firebaseRepo.isSignIn
        _isCurrentUser =firebaseRepo.isCurrentUser
    }


    fun signIn(email:String,password:String) = firebaseRepo.signIn(email,password)

    fun checkCurrentUser() =firebaseRepo.checkCurrentUser()



}