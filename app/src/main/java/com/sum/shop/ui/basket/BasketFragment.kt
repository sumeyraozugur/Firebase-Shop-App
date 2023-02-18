package com.sum.shop.ui.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentBasketBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.sent
import com.sum.shop.utils.showErrorSnackBar

class BasketFragment : Fragment(R.layout.fragment_basket ) {
    private val binding by viewBinding(FragmentBasketBinding::bind)
    private lateinit var  adapter:BasketAdapter

    private lateinit var viewModel: BasketViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        adapter = BasketAdapter(viewModel)


        binding.rvBasket.adapter = adapter
        initObserver()


        with(viewModel){

            adapter.onRemoveBasketClick = {
                deleteFromBasket(it)
                viewModel.resetTotalAmount()
            }

            adapter.onIncreaseClick= {
                increase(it)
            }

            adapter.onDecreaseClick= {
                decrease(it)
            }
        }
    }


    private fun initObserver() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            viewModel.totalBasket()

            adapter.updateList(basketList)
            binding.btnCheckout.setOnClickListener {
                if(basketList.isNotEmpty()){
                    Navigation.sent(it, R.id.action_basketFragment_to_paymentFragment)
                }else{
                    showErrorSnackBar(requireContext(), requireView(), getString(R.string.empty_bag), true)
                }
            }
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) {
            binding.tvAmount.text = it.toString()

        }
    }


}