package com.sum.shop.ui.basket

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel(application: Application) : AndroidViewModel(application) {
    val readAllBasket: LiveData<List<BasketModel>>
    private val repository= ProductRepository(application)

    val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    init {
        readAllBasket = repository.readAllBasket
    }


    fun deleteFromBasket(basketId: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFromBasket(basketId)
        }
    }

    fun updateBasket(product: BasketModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBasket(product)
        }
    }


    fun increase(price: Double) {
        _totalAmount.value = _totalAmount.value?.plus(price)
    }

    fun decrease(price: Double) {
        _totalAmount.value = _totalAmount.value?.minus(price)
    }

    fun resetTotalAmount() {
        _totalAmount.value = 0.0
    }

    fun totalBasket(){
        viewModelScope.launch(Dispatchers.Main) {
            _totalAmount.value = repository.totalBasket()
        }
    }

}