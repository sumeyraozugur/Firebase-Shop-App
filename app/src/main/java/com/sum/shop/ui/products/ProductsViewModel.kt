package com.sum.shop.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val firebaseRepo: ProductRepository) : ViewModel() {

    var categoryList = MutableLiveData<List<ProductModel>>()
    var path = firebaseRepo.path

    fun getProduct(path: String) = viewModelScope.launch {
        categoryList.value = firebaseRepo.getProductRealtime(path)
    }
}



