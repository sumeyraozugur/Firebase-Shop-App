package com.sum.shop.ui.man

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.FirebaseAuthRepository
import com.sum.shop.repository.FirebaseRepository

class ManViewModel:ViewModel() {

    private val firebaseRepo = FirebaseRepository()

    private var _manList = MutableLiveData<List<ProductModel>>()
    val manList: LiveData<List<ProductModel>>
        get() = _manList

    init {
        getProductMan()
    }

    fun getProductMan(){
        firebaseRepo.getProductManRealtime()
        _manList = firebaseRepo.manList
    }

}