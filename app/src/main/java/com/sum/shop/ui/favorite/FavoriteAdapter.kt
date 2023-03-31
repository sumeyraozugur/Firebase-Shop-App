package com.sum.shop.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sum.shop.R
import com.sum.shop.databinding.ItemFavBinding
import com.sum.shop.model.FavModel
import com.sum.shop.utils.diffutil.DiffUtilCallback
import com.sum.shop.utils.extension.loadImage

class FavoriteAdapter(private val onRemoveFavClick: (FavModel) -> Unit = {}) :
    ListAdapter<FavModel, FavoriteAdapter.FavoriteHolder>(
        DiffUtilCallback<FavModel>(
            itemsTheSame = { oldItem, newItem ->
                oldItem == newItem
            },
            contentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding = ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(currentList[position])
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

    override fun getItemCount() = currentList.size
}
