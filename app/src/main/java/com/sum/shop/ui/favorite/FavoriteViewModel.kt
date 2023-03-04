package com.sum.shop.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.FavModel
import com.sum.shop.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository= ProductRepository(application)
    var favList = MutableLiveData<List<FavModel>>()

    init {
        getAllFav()
        favList = repository.returnFavList()
    }


    fun deleteFromFav(fav:FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(fav)
            getAllFav() //to update the list as soon as it is deleted
        }
    }

    private fun getAllFav() {
        repository.getAllFav()
    }
}