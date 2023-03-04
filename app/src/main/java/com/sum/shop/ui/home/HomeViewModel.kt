package com.sum.shop.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.utils.sent

class HomeViewModel():ViewModel() {

    fun navigateToAddProduct(view:View){
        Navigation.sent(view, R.id.action_homeFragment_to_addProductFragment)

    }
}