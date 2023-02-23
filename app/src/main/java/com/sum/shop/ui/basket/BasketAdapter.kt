package com.sum.shop.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemBasketBinding
import com.sum.shop.model.BasketModel

class BasketAdapter(private val viewModel: BasketViewModel) : RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    private var basketList = listOf<BasketModel>()
    var onRemoveBasketClick: (String) -> Unit = {}
    var onIncreaseClick: (Double) -> Unit = {}
    var onDecreaseClick: (Double) -> Unit = {}



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

                tvAmount.text =  basketModel.count.toString()

                btnDelete.setOnClickListener {
                    onRemoveBasketClick(basketModel.uuid)
                }

                btnMinus.setOnClickListener {
                    if ( basketModel.count != 1) {
                        onDecreaseClick(basketModel.productPrice.toDouble())
                         basketModel.count--
                        viewModel.updateBasket(basketModel)
                        tvAmount.text =  basketModel.count.toString()
                    } else {
                        onRemoveBasketClick(basketModel.uuid)
                    }
                }

                btnPlus.setOnClickListener {
                    onIncreaseClick(basketModel.productPrice.toDouble())
                     basketModel.count++
                    viewModel.updateBasket(basketModel)
                    tvAmount.text =   basketModel.count.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun updateList(updatedList: List<BasketModel>) {
        this.basketList = updatedList
        notifyDataSetChanged()
    }
}