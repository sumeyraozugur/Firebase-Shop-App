package com.sum.shop.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.FavModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    var favList = MutableLiveData<List<FavModel>>()

    init {
        getAllFav()
        favList = repository.returnFavList()
    }


    fun deleteFromFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(fav)
            getAllFav() //to update the list as soon as it is deleted
        }
    }

    private fun getAllFav() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFav()
        }
    }
}