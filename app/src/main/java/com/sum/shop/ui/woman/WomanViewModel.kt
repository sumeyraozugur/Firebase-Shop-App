package com.sum.shop.ui.woman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.FireBaseRepository

class WomanViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _womanList = MutableLiveData<List<ProductModel>>()
    val womanList: LiveData<List<ProductModel>>
        get() = _womanList

    init {
        getProductWoman()
    }

     fun getProductWoman(){
        firebaseRepo.getProductWomanRealtime()
         _womanList = firebaseRepo.womanList
    }
}