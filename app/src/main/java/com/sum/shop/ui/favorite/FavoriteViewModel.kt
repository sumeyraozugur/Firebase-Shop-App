package com.sum.shop.ui.favorite

import RoomProductRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFav: LiveData<List<FavModel>>
    private val repository: RoomProductRepository

    init {
        val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
        val favDao = FavProductDatabase.getDatabase(application).favDao()
        repository = RoomProductRepository(favDao,basketDao)
        readAllFav = repository.readAllFav
    }

    fun updateToFav(fav: FavModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodo(fav)
        }
    }

    fun addFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFav(fav)
            Log.v("addViewModel", fav.productTitle)
        }
    }

    fun deleteTodo(favId: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFav(favId)
        }
    }



}