package com.sum.shop.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sum.shop.model.FavModel

@Dao
interface FavProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(todo: FavModel)

    @Query("SELECT * FROM fav_table ORDER BY fav_id ASC")
    fun readAllFav(): LiveData<List<FavModel>>

    @Delete
    suspend fun deleteFromFav(fav: FavModel)



}