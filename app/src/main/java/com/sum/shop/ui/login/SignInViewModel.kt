package com.sum.shop.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class SignInViewModel : ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isSignIn= MutableLiveData<Boolean>()
    val isSingnIn: LiveData<Boolean>
        get()=_isSignIn


    init {
        _isSignIn = firebaseRepo.isSignIn

    }


    fun signIn(email:String,password:String) = firebaseRepo.signIn(email,password)

    fun checkCurrentUser() =firebaseRepo.checkCurrentUser()



}