package com.sum.shop.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.model.ProductModel
import com.sum.shop.repository.FirebaseRepository

class ProductsViewModel : ViewModel() {
    private val firebaseRepo = FirebaseRepository()

    var categoryList = MutableLiveData<List<ProductModel>>()
     var path = firebaseRepo.path



    fun getProduct(path: String) {
        when (path) {
            "man" -> {
                firebaseRepo.getProductRealtime(path)
                categoryList = firebaseRepo.categoryList
            }
            "woman" -> {
                firebaseRepo.getProductRealtime(path)
                categoryList = firebaseRepo.categoryList
            }
            "children" -> {
                firebaseRepo.getProductRealtime(path)
                categoryList = firebaseRepo.categoryList

            }
        }
    }
}














