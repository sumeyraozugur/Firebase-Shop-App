package com.sum.shop.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "basket_table", primaryKeys = ["basket_id", "uuid"])
data class BasketModel(

    @ColumnInfo(name = "basket_id")
    val id: Int = 0,

    @ColumnInfo(name = "uuid")
    val uuid: String = "",

    @ColumnInfo(name = "basket_img")
    val img: String,

    @ColumnInfo(name = "basket_title")
    val productTitle: String,

    @ColumnInfo(name = "basket_price")
    val productPrice: Int,

    @ColumnInfo(name = "basket_count")
    val productCount: String
) {
    var count = 1
}
//val abc = BasketModel().copy(productCount = "asasa") //keep in mind