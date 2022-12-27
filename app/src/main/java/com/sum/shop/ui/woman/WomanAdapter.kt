package com.sum.shop.ui.woman

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemWomanListBinding
import com.sum.shop.model.ProductModel

class WomanAdapter : RecyclerView.Adapter<WomanAdapter.WomanViewHolder>() {
    private var womanList = listOf<ProductModel>()
    var onWomanClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WomanViewHolder {
        val binding =
            ItemWomanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WomanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WomanViewHolder, position: Int) {
        holder.bind(womanList[position])
    }

    inner class WomanViewHolder(private var binding: ItemWomanListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModel) {
            with(binding) {
                itemWomanName.text = item.productTitle
                Glide.with(binding.itemWomanImage).load(item.img).into(binding.itemWomanImage)
            }
        }
    }

    override fun getItemCount() = womanList.size

    fun setData(woman: List<ProductModel>) {
        this.womanList = woman
        notifyDataSetChanged()
    }
}