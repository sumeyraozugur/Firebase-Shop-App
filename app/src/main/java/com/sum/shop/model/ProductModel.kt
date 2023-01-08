package com.sum.shop.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: String,
    val img: String,
    val productTitle: String,
    val productDescription:String,
    val productPrice:String,
    val productCount:String
    ) : Parcelable