package com.sum.shop.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sum.shop.R
import com.sum.shop.databinding.FragmentFavoriteBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy { FavoriteAdapter(viewModel::deleteFromFav) }
//    private val adapter by lazy {
//        FavoriteAdapter(onRemoveFavClick = {
//            viewModel.deleteFromFav(it)
//        })
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObserver()

        binding.rvFav.adapter = adapter
    }


    private fun initializeObserver() {
        viewModel.favList.observe(viewLifecycleOwner) { favList ->
            adapter.updateList(favList)
            if (favList.isEmpty()) {
                binding.tvFavEmpty.visible()
            }
        }
    }
}