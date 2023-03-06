package com.sum.shop.ui.addproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val firebaseRepo: ProductRepository
) : ViewModel() {


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