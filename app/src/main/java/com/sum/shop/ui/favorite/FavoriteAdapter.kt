package com.sum.shop.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.ItemFavBinding
import com.sum.shop.model.FavModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteAdapter( private val viewModel: FavoriteViewModel) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private var favList = listOf<FavModel>()
    var onRemoveFavClick: (FavModel) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding = ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(favList[position])

    }

    inner class FavoriteHolder(private var itemFavBinding: ItemFavBinding) :
        RecyclerView.ViewHolder(itemFavBinding.root) {

        fun bind(favModel: FavModel) {
            itemFavBinding.apply {
                tvFavName.text = favModel.productTitle
                tvFavPrice.text = "${favModel.productPrice} TL"
                favModel.img.let {
                    Glide.with(ivFav).load(favModel.img).into(ivFav)
                }

                ivFavBtn.setOnClickListener {
                    onRemoveFavClick(favModel)
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun updateList(list: List<FavModel>) {
        this.favList = list
        notifyDataSetChanged()
    }
}


/*
                    if (favModel.isFav) {
                        favModel.isFav = false
                        //Update Current User
                        val updateFavModel =
                            FavModel(favModel.id, favModel.img, favModel.productTitle,
                                favModel.productDescription,favModel.productPrice,favModel.isFav)
                        viewModel.updateToFav(updateFavModel)
                        //viewModel.addFav(favModel)
                        ivFavBtn.setBackgroundResource(R.drawable.ic_favorite) }
                    else {
                        favModel.isFav = true
                        val updateFavModel =
                            FavModel(favModel.id, favModel.img, favModel.productTitle,
                                favModel.productDescription,favModel.productPrice,favModel.isFav)
                        viewModel.updateToFav(updateFavModel)
                        ivFavBtn.setBackgroundResource(R.drawable.ic_full_fav)

                    }

                     if (favModel.isFav) {
                         ivFavBtn.setBackgroundResource(R.drawable.ic_full_fav)
                         println(favModel.isFav)}
                     else {
                         ivFavBtn.setBackgroundResource(R.drawable.ic_favorite)
                         println(favModel.isFav)
                     }*/