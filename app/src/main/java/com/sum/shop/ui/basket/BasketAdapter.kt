package com.sum.shop.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemBasketBinding
import com.sum.shop.model.BasketModel

class BasketAdapter() : RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    private var basketList = listOf<BasketModel>()
    var onRemoveBasketClick: (Int) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.bind(basketList[position])

    }

    inner class BasketHolder(private var itemBasketBinding: ItemBasketBinding) :
        RecyclerView.ViewHolder(itemBasketBinding.root) {
        fun bind(basketModel: BasketModel) {

            itemBasketBinding.apply {
                tvBasketName.text = basketModel.productTitle
                tvBasketPrice.text = "${basketModel.productPrice} TL"
                basketModel.img.let {
                    Glide.with(ivBasket).load(basketModel.img).into(ivBasket)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun updateList(list: List<BasketModel>) {
        this.basketList = list
        notifyDataSetChanged()
    }
}