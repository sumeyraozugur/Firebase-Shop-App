package com.sum.shop.utils.resource

sealed class Resource<out T : Any> {
    object Loading : Resource<Nothing>()
    data class Success(val data: Boolean) : Resource<Boolean>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}