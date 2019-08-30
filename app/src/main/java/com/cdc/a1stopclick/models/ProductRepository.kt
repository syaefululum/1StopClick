package com.cdc.a1stopclick.models

interface ProductRepository {
    fun getProducts(callback: RepositoryCallback<Product>, page: Int, size : Int)
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: ArrayList<Data>?)
    fun onError()
}
