package com.sum.shop.utils.diffutil

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T : Any>(
    private val itemsTheSame: (T, T) -> Boolean = { _, _ -> false },
    private val contentsTheSame: (T, T) -> Boolean = { _, _ -> false }
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return itemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return contentsTheSame(oldItem, newItem)
    }
}