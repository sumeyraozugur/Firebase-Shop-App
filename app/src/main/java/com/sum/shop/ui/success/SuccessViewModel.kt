package com.sum.shop.ui.success


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    val readAllBasket: LiveData<List<BasketModel>> = repository.readAllBasket

    fun deleteFromBasket(basketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromBasket(basketId)
        }
    }
}