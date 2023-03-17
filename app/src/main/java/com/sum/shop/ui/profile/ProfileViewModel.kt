package com.sum.shop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProfileModel
import com.sum.shop.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val firebaseRepo: FirebaseAuthRepository) : ViewModel() {

    private var _profileInfo = firebaseRepo.profileInfo
    val profileInfo: LiveData<ProfileModel> = _profileInfo

    private var _isLoading = firebaseRepo.isLoading
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getProfileInfo()
    }

    private fun getProfileInfo() {
        firebaseRepo.getProfileInfo()
        _profileInfo = firebaseRepo.profileInfo
    }

    fun signOut() = firebaseRepo.signOut()
}