package com.sum.shop.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel

@Database(entities = [FavModel::class, BasketModel::class], version = 1, exportSchema = false)
abstract class FavProductDatabase : RoomDatabase() {
    abstract fun favDao(): FavProductDao
    abstract fun basketDao(): BasketProductDao
}
