package com.sum.shop.ui.addproduct

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sum.shop.repository.ProductRepository

class AddProductViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseRepo = ProductRepository(application)

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    init {
        _isSuccess = firebaseRepo.isSuccess
        _isLoading = firebaseRepo.isLoading
    }


    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String
    ) {

        firebaseRepo.addProduct(
            img,
            productTitle,
            productPrice,
            productDescription,
            productQuantiles,
            productType
        )
    }
}