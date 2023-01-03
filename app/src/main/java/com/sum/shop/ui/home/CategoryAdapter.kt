package com.sum.shop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemCategoryBinding
import com.sum.shop.model.CategoryModel

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHoler>() {
    private var categoryList = listOf<CategoryModel>()
    var categoryClick: (Int) -> Unit = {}

    inner class CategoryViewHoler(private var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryModel,position: Int) {
            with(binding){
                Glide.with(binding.ivCategories).load(item.categoryImage)
                    .into(binding.ivCategories)
                itemView.setOnClickListener {
                  categoryClick(position)

                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHoler {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHoler(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHoler, position: Int) {
        holder.bind(categoryList[position],position)
    }

    override fun getItemCount() = categoryList.size

    fun setData(category: List<CategoryModel>) {
        this.categoryList = category
        notifyDataSetChanged()
    }

}