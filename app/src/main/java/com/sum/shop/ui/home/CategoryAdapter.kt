package com.sum.shop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemCategoryBinding
import com.sum.shop.model.CategoryModel

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHoler>() {
    private var categoryList = listOf<CategoryModel>()

    inner class CategoryViewHoler(private var itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {

        fun bind(item: CategoryModel) {
            with(itemCategoryBinding){
                Glide.with(itemCategoryBinding.ivCategories).load(item.categoryImage)
                    .into(itemCategoryBinding.ivCategories)
                root.setOnClickListener {
                 // categoryClick(position)
                    val action =HomeFragmentDirections.actionHomeFragmentToProductsFragment(item.categoryName)
                    Navigation.findNavController(it).navigate(action)
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
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size

    fun setData(category: List<CategoryModel>) {
        this.categoryList = category
        notifyDataSetChanged()
    }

}