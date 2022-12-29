package com.sum.shop.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sum.shop.Constant.SIGN_IN
import com.sum.shop.Constant.SIGN_UP
import com.sum.shop.MainActivity
import com.sum.shop.R
import com.sum.shop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

          Firebase.auth.currentUser?.let {
              val intent = Intent(this, MainActivity::class.java)
              startActivity(intent)
              finish()
          }

        val titleList = arrayListOf(SIGN_IN, SIGN_UP)

        binding.viewPager.adapter = TabAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.loginTabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()


    }


}