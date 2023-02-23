package com.sum.shop.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "fav_table", primaryKeys = ["fav_id", "uuid"])
data class FavModel(

    @ColumnInfo(name = "fav_id")
    var id: Long = 0,

    @ColumnInfo(name = "uuid")
    var uuid: String = "",

    @ColumnInfo(name = "fav_img")
    val img: String,

    @ColumnInfo(name = "fav_title")
    val productTitle: String,

    @ColumnInfo(name = "fav_description")
    val productDescription: String,

    @ColumnInfo(name = "fav_price")
    val productPrice: String

)