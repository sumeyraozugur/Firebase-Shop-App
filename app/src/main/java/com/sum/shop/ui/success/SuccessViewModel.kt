package com.sum.shop.ui.success


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.model.BasketModel
import com.sum.shop.repository.ProductRepository
import com.sum.shop.utils.sent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val readAllBasket: LiveData<List<BasketModel>>

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