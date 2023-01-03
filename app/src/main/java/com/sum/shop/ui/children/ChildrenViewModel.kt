package com.sum.shop.ui.children

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.FireBaseRepository

class ChildrenViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _childrenList = MutableLiveData<List<ProductModel>>()
    val childrenList: LiveData<List<ProductModel>>
        get() = _childrenList

    init {
        getProductAppliance()
    }

    fun getProductAppliance(){
        firebaseRepo.getProductAppliancesRealtime()
        _childrenList = firebaseRepo.childrenList
    }
}