package com.sum.shop.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    fun addToFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFav(fav)
        }
    }

    fun addToBasket(basket: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToBasket(basket)
        }
    }

    fun deleteFromFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(fav)
        }
    }
}
