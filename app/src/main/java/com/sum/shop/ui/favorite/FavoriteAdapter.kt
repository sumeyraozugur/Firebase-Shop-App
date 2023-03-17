package com.sum.shop.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sum.shop.R
import com.sum.shop.databinding.ItemFavBinding
import com.sum.shop.model.FavModel
import com.sum.shop.utils.extension.loadImage

class FavoriteAdapter(private val onRemoveFavClick: (FavModel) -> Unit = {}) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private var favList = listOf<FavModel>()


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
                val productPrice =
                    itemView.context.getString(R.string.total_tl, favModel.productPrice)// 10Tl like
                tvFavPrice.text = productPrice
                ivFav.loadImage(favModel.img)

                ivFavBtn.setOnClickListener {
                    onRemoveFavClick(favModel)
                }
            }
        }
    }

    override fun getItemCount() = favList.size


    fun updateList(list: List<FavModel>) {
        this.favList = list
        notifyDataSetChanged()
    }
}
