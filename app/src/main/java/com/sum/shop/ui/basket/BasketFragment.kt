package com.sum.shop.ui.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentBasketBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.showErrorSnackBar

class BasketFragment : Fragment(R.layout.fragment_basket ) {
    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val adapter by lazy { BasketAdapter(viewModel) }
    private val  viewModel by lazy { BasketViewModel(requireActivity().application) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBasket.adapter = adapter
        initializeObserver()


        with(viewModel){

            adapter.onRemoveBasketClick = {
                deleteFromBasket(it)
                resetTotalAmount()
            }

            adapter.onIncreaseClick= {
                increase(it)
            }

            adapter.onDecreaseClick= {
                decrease(it)
            }
        }
    }


    private fun initializeObserver() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->

            viewModel.totalBasket()
            adapter.updateList(basketList)

            if(basketList.isEmpty()){
                binding.tvBasketEmpty.visibility = View.VISIBLE
            }


            binding.btnCheckout.setOnClickListener {
                if(basketList.isNotEmpty()){
                    val action =
                        viewModel._totalAmount.value?.let { it1 ->
                            BasketFragmentDirections.actionBasketFragmentToPaymentFragment(it1.toFloat())
                        }
                    if (action != null) {
                        Navigation.findNavController(it).navigate(action)
                    }
                }
                else{
                    requireView().showErrorSnackBar(getString(R.string.empty_bag),true)
                }
            }
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) {
            binding.tvAmount.text = it.toString()
        }
    }
}