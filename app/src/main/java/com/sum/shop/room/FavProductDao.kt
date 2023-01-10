package com.sum.shop.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.*
import com.sum.shop.model.FavModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Dao
interface FavProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(todo: FavModel)

    @Query("SELECT * FROM fav_table ORDER BY fav_id ASC")
    fun readAllFav(): LiveData<List<FavModel>>

    @Update
    suspend fun updateFav(fav: FavModel)

    @Query("DELETE FROM fav_table WHERE fav_id = :idInput")
    suspend fun deleteFromFav(idInput: Int)



}