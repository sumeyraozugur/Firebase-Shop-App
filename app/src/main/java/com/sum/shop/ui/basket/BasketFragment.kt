package com.sum.shop.ui.basket

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentBasketBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent

class BasketFragment : Fragment(R.layout.fragment_basket ) {
    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val adapter by lazy { BasketAdapter() }
    private lateinit var viewModel: BasketViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]

        binding.rvBasket.adapter = adapter
        initObserver()

        adapter.onRemoveBasketClick = {
            viewModel.deleteFromBasket(it)
        }
        binding.btnCheckout.setOnClickListener {
            Navigation.sent(it, R.id.action_basketFragment_to_paymentFragment)
            //Toast.makeText(context,"Sumeyra", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObserver() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            adapter.updateList(basketList)
        }
    }


}