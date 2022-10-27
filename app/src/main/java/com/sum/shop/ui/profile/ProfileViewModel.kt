package com.sum.shop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FireBaseRepository

class ProfileViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _profileInfo = MutableLiveData<ProfileModel>()
    val profileInfo: LiveData<ProfileModel>
        get() = _profileInfo

    private var _updateInfo = MutableLiveData<ProfileModel>()
    val updateInfo: LiveData<ProfileModel>
        get() = _updateInfo

    init {
        getProfileInfo()

    }

    fun getProfileInfo(){
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun updateProfile(firstName: String, lastName: String, email: String){
        firebaseRepo.updateProfile(firstName,lastName,email)
        _updateInfo= firebaseRepo.updateInfo

    }

    fun signOut(){
        firebaseRepo.signOut()
    }

}