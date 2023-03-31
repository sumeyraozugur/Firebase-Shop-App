package com.sum.shop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sum.shop.databinding.ItemCategoryBinding
import com.sum.shop.model.CategoryModel
import com.sum.shop.utils.diffutil.DiffUtilCallback
import com.sum.shop.utils.extension.loadImage

class CategoryAdapter(private val onClickCategory: (CategoryModel) -> Unit) :
    ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(
        DiffUtilCallback<CategoryModel>(
            itemsTheSame = { oldItem, newItem ->
                oldItem == newItem
            },
            contentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    ) {

    inner class CategoryViewHolder(private var itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {

        fun bind(categoryModel: CategoryModel) {
            with(itemCategoryBinding) {
                ivCategories.loadImage(categoryModel.categoryImage)
                root.setOnClickListener {
                    onClickCategory(categoryModel)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount() = currentList.size


}