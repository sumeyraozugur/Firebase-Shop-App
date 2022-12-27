package com.sum.shop.ui.homeappliances

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.FireBaseRepository

class HomeAppliancesViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _applianceList = MutableLiveData<List<ProductModel>>()
    val applianceList: LiveData<List<ProductModel>>
        get() = _applianceList

    init {
        getProductAppliance()
    }

    fun getProductAppliance(){
        firebaseRepo.getProductAppliancesRealtime()
        _applianceList = firebaseRepo.applianceList
    }
}