package com.sum.shop.ui.children

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemChildrenListBinding
import com.sum.shop.model.ProductModel
import com.sum.shop.utils.visible

class ChildrenAdapter : RecyclerView.Adapter<ChildrenAdapter.HomeViewHolder>() {
    private var childrenList = listOf<ProductModel>()

    inner class HomeViewHolder(private var binding: ItemChildrenListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            with(binding) {
                tvChildrenName.text = item.productTitle
                //itemApplianceDescription.text =item.productDescription
                tvChildrenPrice.text = "${item.productPrice} TL"
                Glide.with(binding.ivChildren).load(item.img)
                    .into(binding.ivChildren)
                if (item.productCount.toInt() <= 3) {
                    tvChildrenCount.visible()
                    tvChildrenCount.text = "Only ${item.productCount} left in stock " // order soon
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemChildrenListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(childrenList[position])
    }

    override fun getItemCount() = childrenList.size

    fun setData(appliance: List<ProductModel>) {
        this.childrenList = appliance
        notifyDataSetChanged()
    }


}