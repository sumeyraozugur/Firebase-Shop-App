package com.sum.shop.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemProductsBinding
import com.sum.shop.model.ProductModel
import com.sum.shop.utils.visible

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    private var productsList = listOf<ProductModel>()
    var onProductsClick: (Int) -> Unit = {}

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

        fun bind(item: ProductModel) {
            with(binding) {
                tvProductName.text = item.productTitle
                tvProductPrice.text = "${item.productPrice} TL"
                Glide.with(binding.ivProduct).load(item.img).into(binding.ivProduct)
                if (item.productCount.toInt() <= 3) {
                    tvProductCount.visible()
                    tvProductCount.text = "Only ${item.productCount} left in stock " // order soon
                }

                root.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount() = productsList.size

    fun setData(product: List<ProductModel>) {
        this.productsList = product

        notifyDataSetChanged()
    }
}