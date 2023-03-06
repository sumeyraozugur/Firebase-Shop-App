package com.sum.shop.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sum.shop.R
import com.sum.shop.databinding.FragmentFavoriteBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.loginregister.signin.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val adapter by lazy { FavoriteAdapter(viewModel) }
    private val viewModel:FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObserver()

        adapter.onRemoveFavClick= {
            viewModel.deleteFromFav(it)
        }

        binding.rvFav.adapter = adapter
    }


    private fun initializeObserver() {
        viewModel.favList.observe(viewLifecycleOwner){ favList ->
            adapter.updateList(favList)
            if(favList.isEmpty()){
                binding.tvFavEmpty.visibility = View.VISIBLE
            }
        }
    }
}