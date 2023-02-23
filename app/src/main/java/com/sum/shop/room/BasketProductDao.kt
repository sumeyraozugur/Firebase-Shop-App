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

    @Query("DELETE FROM basket_table WHERE uuid = :idInput")
    suspend fun deleteFromBasket(idInput: String)

    @Query("SELECT SUM(count * basket_price) FROM basket_table")
    suspend fun getTotalPrice(): Double

    @Update
    suspend fun updateBasket(basket: BasketModel) // For count in adapter
}