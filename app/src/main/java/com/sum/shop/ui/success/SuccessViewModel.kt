package com.sum.shop.ui.success

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.model.BasketModel
import com.sum.shop.repository.ProductRepository
import com.sum.shop.utils.sent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuccessViewModel(application: Application) : AndroidViewModel(application) {
    val readAllBasket: LiveData<List<BasketModel>>
    private val repository = ProductRepository(application)


    init {
        readAllBasket = repository.readAllBasket
    }

    fun deleteFromBasket(basketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromBasket(basketId)
        }
    }

    fun navigateHome(view:View){
        Navigation.sent(view, R.id.action_successFragment_to_homeFragment)
    }
}