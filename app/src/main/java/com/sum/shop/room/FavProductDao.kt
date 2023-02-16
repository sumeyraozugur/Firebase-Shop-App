package com.sum.shop.room

import androidx.room.*
import com.sum.shop.model.FavModel

@Dao
interface FavProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(fav: FavModel)

    @Query("SELECT * FROM fav_table ORDER BY fav_id ASC")
    suspend fun getAllFav(): List<FavModel>

    @Query("SELECT fav_title FROM fav_table")
    suspend fun getFavoritesTitles(): List<String>?

    @Delete
    suspend fun deleteFromFav(fav:FavModel)




}