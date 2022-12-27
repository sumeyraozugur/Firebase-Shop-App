package com.sum.shop.ui.homeappliances

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemApplianceListBinding
import com.sum.shop.model.ProductModel

class HomeApplianceAdapter : RecyclerView.Adapter<HomeApplianceAdapter.HomeViewHolder>() {
    private var applianceList = listOf<ProductModel>()

    inner class HomeViewHolder(private var binding: ItemApplianceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            with(binding) {
                itemApplianceName.text = item.productTitle
                Glide.with(binding.itemApplianceImage).load(item.img)
                    .into(binding.itemApplianceImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemApplianceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(applianceList[position])
    }

    override fun getItemCount() = applianceList.size

    fun setData(appliance: List<ProductModel>) {
        this.applianceList = appliance
        notifyDataSetChanged()
    }


}