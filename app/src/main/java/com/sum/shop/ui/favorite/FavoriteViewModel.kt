package com.sum.shop.ui.favorite

import RoomProductRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFav: LiveData<List<FavModel>>
    private val repository: RoomProductRepository

    init {
        val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
        val favDao = FavProductDatabase.getDatabase(application).favDao()
        repository = RoomProductRepository(favDao,basketDao)
        readAllFav = repository.readAllFav
    }
}