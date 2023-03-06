package com.sum.shop.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sum.shop.model.BasketModel


//@Database(entities = [BasketModel::class], version = 1, exportSchema = false)
//abstract class BasketProductDatabase : RoomDatabase() {
//    abstract fun basketDao(): BasketProductDao
//}

@Database(entities = [BasketModel::class], version = 1, exportSchema = false)
abstract class BasketProductDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketProductDao

    companion object {
        private var INSTANCE: BasketProductDatabase? = null

        fun getDatabase(context: Context): BasketProductDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BasketProductDatabase::class.java,
                    "basket_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }


}