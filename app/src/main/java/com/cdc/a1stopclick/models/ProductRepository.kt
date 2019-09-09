package com.cdc.a1stopclick.models

interface ProductRepository {
    //fun getProducts(callback: RepositoryCallback<Product>, page: Int?, size: Int?)
    fun searchProducts(callback: RepositoryCallback<Product>, q: String?, page: Int?, size: Int?)
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: ArrayList<Data>?)
    fun onError()
}
