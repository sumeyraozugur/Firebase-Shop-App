package com.sum.shop.ui.addproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sum.shop.repository.FireBaseRepository

class AddProductViewModel:ViewModel() {
    private val firebaseRepo = FireBaseRepository()

    private var _isLoadProduct= MutableLiveData<Boolean>()
    val isLoadProduct: LiveData<Boolean>
        get()=_isLoadProduct


    init {
        _isLoadProduct = firebaseRepo.isLoadProduct
    }



    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice:String,
        productDescription: String,
        productQuantiles: String,
        productType:String
        ){

        firebaseRepo.addProduct(img,productTitle,productPrice,productDescription, productQuantiles,productType)

    }
}