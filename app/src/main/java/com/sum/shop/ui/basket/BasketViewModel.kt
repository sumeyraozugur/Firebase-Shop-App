package com.sum.shop.ui.basket

import RoomProductRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sum.shop.model.BasketModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase

class BasketViewModel(application: Application) : AndroidViewModel(application) {
    val readAllBasket: LiveData<List<BasketModel>>
    private val repository: RoomProductRepository

    init {
        val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
        val favDao = FavProductDatabase.getDatabase(application).favDao()
        repository = RoomProductRepository(favDao, basketDao)
        readAllBasket = repository.readAllBasket
    }
}