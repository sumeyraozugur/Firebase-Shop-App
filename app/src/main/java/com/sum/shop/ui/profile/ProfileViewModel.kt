package com.sum.shop.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FirebaseAuthRepository
import com.sum.shop.utils.sent

class ProfileViewModel:ViewModel() {
    private val firebaseRepo = FirebaseAuthRepository()

    private var _profileInfo = MutableLiveData<ProfileModel>()
    val profileInfo: LiveData<ProfileModel>
        get() = _profileInfo

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        getProfileInfo()
        _isLoading = firebaseRepo.isLoading
    }

    fun getProfileInfo(){
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun signOut(){
        firebaseRepo.signOut()
    }

    fun navigateToUpdate(view: View){
        Navigation.sent(view, R.id.action_profileFragment_to_updateProfileFragment)
    }

}