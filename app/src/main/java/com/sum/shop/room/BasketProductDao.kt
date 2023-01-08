package com.sum.shop.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sum.shop.model.BasketModel

@Dao
interface BasketProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBasket(todo: BasketModel)

    @Query("SELECT * FROM basket_table ORDER BY basket_id ASC")
    fun readAllBasket(): LiveData<List<BasketModel>>

    @Delete
    suspend fun deleteFromBasket(basket: BasketModel)



}