package com.sum.shop.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sum.shop.R
import com.sum.shop.databinding.ItemBasketBinding
import com.sum.shop.model.BasketModel
import com.sum.shop.utils.extension.loadImage
import com.sum.shop.utils.extension.showErrorSnackBar

class BasketAdapter(
    private val onRemoveBasketClick: (String) -> Unit = {},
    private val onIncreaseClick: (BasketModel) -> Unit = {},
    private val onDecreaseClick: (BasketModel) -> Unit = {}
) : RecyclerView.Adapter<BasketAdapter.BasketHolder>() {

    private var basketList = listOf<BasketModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BasketHolder(
        ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BasketHolder, position: Int) =
        holder.bind(basketList[position])

    inner class BasketHolder(private var itemBasketBinding: ItemBasketBinding) :
        RecyclerView.ViewHolder(itemBasketBinding.root) {

        fun bind(basketModel: BasketModel) = with(itemBasketBinding) {

            tvBasketName.text = basketModel.productTitle
            val productPrice = "${basketModel.productPrice} TL"
            tvBasketPrice.text = productPrice
            ivBasket.loadImage(basketModel.img)

            tvAmount.text = basketModel.count.toString()

            btnDelete.setOnClickListener {
                onRemoveBasketClick(basketModel.uuid)
            }

            btnMinus.setOnClickListener {
                if (basketModel.count != 1) {
                    onDecreaseClick(basketModel)
                    basketModel.count--
                    tvAmount.text = basketModel.count.toString()
                } else {
                    onRemoveBasketClick(basketModel.uuid)
                }
            }

            btnPlus.setOnClickListener {
                if (basketModel.productCount.toInt() > basketModel.count) {
                    onIncreaseClick(basketModel)
                    basketModel.count++
                    tvAmount.text = basketModel.count.toString()
                } else {
                    it.showErrorSnackBar(it.context.getString(R.string.products_in_stock), true)
                }
            }
        }
    }

    override fun getItemCount() = basketList.size

    fun updateList(updatedList: List<BasketModel>) {
        this.basketList = updatedList
        notifyDataSetChanged()
    }
}