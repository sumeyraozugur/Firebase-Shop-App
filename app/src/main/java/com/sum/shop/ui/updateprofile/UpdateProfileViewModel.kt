package com.sum.shop.ui.updateprofile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val firebaseRepo: FirebaseAuthRepository) :
    ViewModel() {

    private var _profileInfo = firebaseRepo.profileInfo
    val profileInfo: LiveData<ProfileModel> = _profileInfo

    private var _isSuccess = firebaseRepo.isSuccess
    val isSuccess: LiveData<Boolean> = _isSuccess

    private var _isLoading = firebaseRepo.isLoading
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        getProfileInfo()
    }

    private fun getProfileInfo() {
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun updateProfile(firstName: String, lastName: String, email: String, picture: Uri) {
        firebaseRepo.updateProfile(firstName, lastName, email, picture)
        _isSuccess = firebaseRepo.isSuccess
    }
}