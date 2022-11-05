package com.sum.shop.ui.updateprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FireBaseRepository

class UpdateProfileViewModel:ViewModel() {

    private val firebaseRepo = FireBaseRepository()

    private var _profileInfo = MutableLiveData<ProfileModel>()
    val profileInfo: LiveData<ProfileModel>
        get() = _profileInfo

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    init {
        _isSuccess= firebaseRepo.isSuccess
        getProfileInfo()
    }

    fun getProfileInfo(){
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun updateProfile(firstName: String, lastName: String, email: String){
        firebaseRepo.updateProfile(firstName,lastName,email)
        _isSuccess= firebaseRepo.isSuccess
    }

}