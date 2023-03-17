package com.sum.shop.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.ItemProductsBinding
import com.sum.shop.model.ProductModel
import com.sum.shop.utils.extension.visible

class ProductsAdapter(private val onClickDetail: (ProductModel) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    private var productsList = listOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    inner class ProductsViewHolder(private var binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productModel: ProductModel) {
            with(binding) {
                tvProductName.text = productModel.productTitle
                tvProductPrice.text =
                    itemView.context.getString(R.string.total_tl, productModel.productPrice)
                Glide.with(binding.ivProduct).load(productModel.img).into(binding.ivProduct)
                if (productModel.productCount.toInt() <= 3) {
                    tvProductCount.visible()
                    tvProductCount.text = itemView.context.getString(
                        R.string.left_in_stock, productModel.productCount
                    )// order soon
                }

                root.setOnClickListener {
                    onClickDetail(productModel)

                }

                if (productModel.productFav) ivProductFav.setBackgroundResource(R.drawable.ic_full_fav)
                else ivProductFav.setBackgroundResource(R.drawable.ic_favorite_border)

            }
        }
    }

    override fun getItemCount() = productsList.size

    fun setData(product: List<ProductModel>) {
        this.productsList = product
        notifyDataSetChanged()
    }
}