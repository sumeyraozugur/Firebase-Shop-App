package com.sum.shop.ui.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentBasketBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.showErrorSnackBar
import com.sum.shop.utils.extension.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {
    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val viewModel: BasketViewModel by viewModels()
    private val adapter by lazy {
        BasketAdapter(
            onRemoveBasketClick = ::onRemoveBasketClick,
            viewModel::increase,
            viewModel::decrease
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBasket.adapter = adapter
        initializeObserver()
    }

    private fun onRemoveBasketClick(s: String) {
        viewModel.deleteFromBasket(s)
        viewModel.resetTotalAmount()
    }

    private fun initializeObserver() {
        with(binding) {
            viewModel.apply {

                readAllBasket.observe(viewLifecycleOwner) { basketList ->

                    totalBasket()
                    adapter.submitList(basketList)

                    if (basketList.isEmpty()) {
                        tvBasketEmpty.visible()
                    }

                    btnCheckout.setOnClickListener {
                        if (basketList.isNotEmpty()) {
                            val action =
                                totalAmount.value?.let { double ->
                                    BasketFragmentDirections.actionBasketFragmentToPaymentFragment(
                                        double.toFloat()
                                    )
                                }
                            if (action != null) {
                                Navigation.findNavController(it).navigate(action)
                            }
                        } else {
                            requireView().showErrorSnackBar(getString(R.string.empty_bag), true)
                        }
                    }
                }

                totalAmount.observe(viewLifecycleOwner) {
                    if (it == null) tvAmount.text = getString(R.string._0_TL)
                    else tvAmount.text = getString(R.string.total_tl, it.toString())
                }
            }
        }
    }
}