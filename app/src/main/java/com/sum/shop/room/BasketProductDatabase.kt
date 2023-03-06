package com.sum.shop.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sum.shop.model.BasketModel

@Database(entities = [BasketModel::class], version = 1, exportSchema = false)
abstract class BasketProductDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketProductDao
}
