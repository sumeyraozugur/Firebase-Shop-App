package com.sum.shop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FirebaseAuthRepository

class ProfileViewModel:ViewModel() {
    private val firebaseRepo = FirebaseAuthRepository()

    private var _profileInfo = MutableLiveData<ProfileModel>()
    val profileInfo: LiveData<ProfileModel>
        get() = _profileInfo



    init {
        getProfileInfo()
    }

    fun getProfileInfo(){
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun signOut(){
        firebaseRepo.signOut()
    }

}