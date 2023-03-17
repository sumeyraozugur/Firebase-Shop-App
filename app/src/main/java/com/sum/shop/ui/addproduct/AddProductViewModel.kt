package com.sum.shop.ui.addproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val firebaseRepo: ProductRepository,
) : ViewModel() {

    private val _isSuccess = firebaseRepo.isSuccess
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isLoading = firebaseRepo.isLoading
    val isLoading: LiveData<Boolean> = _isLoading

    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String,
    ) = firebaseRepo.addProduct(
        img,
        productTitle,
        productPrice,
        productDescription,
        productQuantiles,
        productType
    )
}