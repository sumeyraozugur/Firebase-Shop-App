package com.sum.shop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_table")
data class FavModel( // id int olarak veremedim
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fav_id")
    val id: Int,

    @ColumnInfo(name = "fav_img")
    val img: String,

    @ColumnInfo(name = "fav_title")
    val productTitle: String,

    @ColumnInfo(name = "fav_description")
    val productDescription:String,

    @ColumnInfo(name = "fav_price")
    val productPrice:String,

    @ColumnInfo(name = "fav_count")
    val productCount:String
)