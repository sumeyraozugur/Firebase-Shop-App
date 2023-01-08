package com.sum.shop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table")
data class BasketModel( // id int olarak veremedim
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "basket_id")
    val id: Int,

    @ColumnInfo(name = "basket_img")
    val img: String,

    @ColumnInfo(name = "basket_title")
    val productTitle: String,

    @ColumnInfo(name = "basket_description")
    val productDescription:String,

    @ColumnInfo(name = "basket_price")
    val productPrice:String,

    @ColumnInfo(name = "basket_count")
    val productCount:String
)