package com.sum.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sum.shop.databinding.ActivityMainBinding
import com.sum.shop.utils.gone
import com.sum.shop.utils.visible

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        //bottom navigation is working correctly
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)


        //put somewhere where you want to see bottom nav
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id !in listOf(
                R.id.signInFragment, R.id.signUpFragment, R.id.loginRegiser, R.id.splashFragment,
                R.id.productsFragment,R.id.addProductFragment ,R.id.productDetailFragment, R.id.forgotPasswordFragment,
                R.id.updateProfileFragment, R.id.paymentFragment, R.id.successFragment
            )
        }
    }

}