package com.sum.shop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table",primaryKeys = ["basket_id", "uuid"])
data class BasketModel( // id int olarak veremedim

    @ColumnInfo(name = "basket_id")
    val id: Int=0,

    @ColumnInfo(name = "uuid")
    var uuid: String = "",

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
){
    var count = 1
}