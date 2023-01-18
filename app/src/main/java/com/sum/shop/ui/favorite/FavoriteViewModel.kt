package com.sum.shop.ui.favorite

import RoomProductRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
    val favDao = FavProductDatabase.getDatabase(application).favDao()
    private val repository: RoomProductRepository = RoomProductRepository(favDao, basketDao)
    var kisilerListesi = MutableLiveData<List<FavModel>>()

    init {
        kisileriYukle()
        kisilerListesi = repository.kisileriGetir()
    }




    fun deleteFromFav(favId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(favId)
            kisileriYukle()
        }
    }

    fun kisileriYukle() {
        repository.getAll()
    }


}