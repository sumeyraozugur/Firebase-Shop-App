package com.sum.shop.ui.updateprofile

import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FirebaseAuthRepository
import com.sum.shop.utils.sent

class UpdateProfileViewModel:ViewModel() {

    private val firebaseRepo = FirebaseAuthRepository()

    private var _profileInfo = MutableLiveData<ProfileModel>()
    val profileInfo: LiveData<ProfileModel>
        get() = _profileInfo

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess


    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading



    init {
        _isSuccess= firebaseRepo.isSuccess
        _isLoading = firebaseRepo.isLoading
        getProfileInfo()
    }

    fun getProfileInfo(){
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun updateProfile(firstName: String, lastName: String, email: String,picture:Uri){
        firebaseRepo.updateProfile(firstName,lastName,email,picture)
        _isSuccess= firebaseRepo.isSuccess
    }

    fun navigateToHome(view: View)= Navigation.sent(view, R.id.action_updateProfileFragment_to_homeFragment)
}