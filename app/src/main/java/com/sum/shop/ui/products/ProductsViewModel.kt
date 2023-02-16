package com.sum.shop.ui.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductsViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseRepo = ProductRepository(application)

    var categoryList = MutableLiveData<List<ProductModel>>()
    var path = firebaseRepo.path

    fun getProduct(path: String) = viewModelScope.launch {
        categoryList.value = firebaseRepo.getProductRealtime(path)
    }
}















