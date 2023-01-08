package com.sum.shop.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemFavBinding
import com.sum.shop.model.FavModel

class FavoriteAdapter() : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private var favList = listOf<FavModel>()
    var onRemoveFavClick: (Int) -> Unit = {}


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