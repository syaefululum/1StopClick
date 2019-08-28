package com.cdc.a1stopclick.models

interface ProductRepository {
    fun getProducts(callback: RepositoryCallback<List<Data>>)
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: List<Data>?)
    fun onError()
}
