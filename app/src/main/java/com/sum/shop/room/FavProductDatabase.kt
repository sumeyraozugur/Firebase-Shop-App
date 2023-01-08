package com.sum.shop.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sum.shop.model.FavModel

@Database(entities = [FavModel::class], version = 1, exportSchema = false)
abstract class FavProductDatabase : RoomDatabase() {
    abstract fun favDao(): FavProductDao

    companion object {
        private var INSTANCE: FavProductDatabase? = null

        fun getDatabase(context: Context): FavProductDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavProductDatabase::class.java,
                    "fav_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }


}