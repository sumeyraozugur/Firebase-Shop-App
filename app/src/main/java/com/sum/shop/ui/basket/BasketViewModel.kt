package com.sum.shop.ui.basket


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val readAllBasket: LiveData<List<BasketModel>> = repository.readAllBasket

    private val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    fun deleteFromBasket(basketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromBasket(basketId)
        }
    }

    private fun updateBasket(product: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBasket(product)
        }
    }


    fun increase(product: BasketModel) {
        _totalAmount.value = _totalAmount.value?.plus(product.productPrice)
        updateBasket(product)
    }

    fun decrease(product: BasketModel) {
        _totalAmount.value = _totalAmount.value?.minus(product.productPrice)
        updateBasket(product)
    }

    fun resetTotalAmount() {
        _totalAmount.value = 0.0
    }

    fun totalBasket() {
        viewModelScope.launch() {
            _totalAmount.value = repository.totalBasket()
        }
    }
}