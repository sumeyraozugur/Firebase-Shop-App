package com.sum.shop.ui.splash

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sum.shop.R
import com.sum.shop.databinding.FragmentSplashBinding
import com.sum.shop.delegate.viewBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

   /*     CoroutineScope(Dispatchers.Main).launch {
            if(Firebase.auth.currentUser !=null){
                findNavController().navigate(R.id.action_splashFragment_to_main_graph)

            }
            else
                findNavController().navigate(R.id.action_splashFragment_to_loginRegiser)
            delay(3500)

        }*/
        binding.lottieSplash.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                if (Firebase.auth.currentUser !=null) {
                    findNavController().navigate(R.id.action_splashFragment_to_main_graph)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginRegister)
                }
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        })

     /*   Handler(Looper.myLooper()!!).postDelayed({

        }, 3500)*/


    }

}