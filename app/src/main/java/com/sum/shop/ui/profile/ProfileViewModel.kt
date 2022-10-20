package com.sum.shop.ui.profile

import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class ProfileViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()


    fun signOut(){
        firebaseRepo.signOut()
    }

}