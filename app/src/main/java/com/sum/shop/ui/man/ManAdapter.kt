package com.sum.shop.ui.man

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemManListBinding
import com.sum.shop.model.ProductModel

class ManAdapter : RecyclerView.Adapter<ManAdapter.ManViewHolder>() {
    private var manList = listOf<ProductModel>()
    var onWomanClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManViewHolder {
        val binding =
            ItemManListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManViewHolder, position: Int) {
        holder.bind(manList[position])
    }

    inner class ManViewHolder(private var binding: ItemManListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductModel) {
            with(binding) {
                itemManName.text = item.productTitle
                Glide.with(binding.itemManImage).load(item.img).into(binding.itemManImage)
            }
        }
    }

    override fun getItemCount() = manList.size

    fun setData(man: List<ProductModel>) {
        this.manList = man
        notifyDataSetChanged()
    }
}
