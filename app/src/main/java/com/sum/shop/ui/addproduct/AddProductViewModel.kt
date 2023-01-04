package com.sum.shop.ui.addproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FirebaseRepository

class AddProductViewModel : ViewModel() {
    private val firebaseRepo = FirebaseRepository()

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess


    init {
        _isSuccess = firebaseRepo.isSuccess
    }


    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String
    ) {

        firebaseRepo.addProductWoman(
            img,
            productTitle,
            productPrice,
            productDescription,
            productQuantiles,
            productType
        )

    }


    fun addProductMan(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String
    ) {

        firebaseRepo.addProductMan(
            img,
            productTitle,
            productPrice,
            productDescription,
            productQuantiles,
            productType
        )

    }


    fun addProductChildren(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String
    ) {
        firebaseRepo.addProductChildren(
            img,
            productTitle,
            productPrice,
            productDescription,
            productQuantiles,
            productType
        )

    }
}