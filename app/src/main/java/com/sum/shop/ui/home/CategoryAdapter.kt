package com.sum.shop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sum.shop.databinding.ItemCategoryBinding
import com.sum.shop.model.CategoryModel

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList = listOf<CategoryModel>()

    inner class CategoryViewHolder(private var itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {

        fun bind(categoryModel: CategoryModel) {
            with(itemCategoryBinding){
                Glide.with(itemCategoryBinding.ivCategories).load(categoryModel.categoryImage)
                    .into(itemCategoryBinding.ivCategories)
                root.setOnClickListener {
                    val action =HomeFragmentDirections.actionHomeFragmentToProductsFragment(categoryModel.categoryName)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size

    fun setData(category: List<CategoryModel>) {
        this.categoryList = category
        notifyDataSetChanged()
    }
}