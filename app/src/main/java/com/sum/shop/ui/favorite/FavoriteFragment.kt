package com.sum.shop.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sum.shop.R
import com.sum.shop.databinding.FragmentFavoriteBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.basket.BasketViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val adapter by lazy { FavoriteAdapter(viewModel) }
    private val  viewModel by lazy { FavoriteViewModel(requireActivity().application) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()

        adapter.onRemoveFavClick= {
            viewModel.deleteFromFav(it)
        }

        binding.rvFav.adapter = adapter
    }

    private fun initObserver() {
        viewModel.kisilerListesi.observe(viewLifecycleOwner){ favList ->
            adapter.updateList(favList)
        }
    }
}