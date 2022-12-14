package com.sum.shop.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sum.shop.R
import com.sum.shop.databinding.FragmentFavoriteBinding
import com.sum.shop.delegate.viewBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val adapter by lazy { FavoriteAdapter(viewModel) }
    private lateinit var viewModel: FavoriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        initObserver()



        binding.rvFav.adapter = adapter

        adapter.onRemoveFavClick= {
            viewModel.deleteTodo(it)
        }


    }

    private fun initObserver() {
        viewModel.readAllFav.observe(viewLifecycleOwner) { favList ->
            adapter.updateList(favList)
        }
    }
}