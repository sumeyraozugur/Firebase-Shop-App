package com.sum.shop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: String? = null,
    val img: String,
    val productTitle: String,
    val productDescription:String,
    val productPrice:String,
    val productCount:String
    ) : Parcelable