package com.cdc.a1stopclick.presenter

import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.ProductRepository
import com.cdc.a1stopclick.models.RepositoryCallback

class HomePresenter(private val repository: ProductRepository): BasePresenter<HomePresenter.View>() {
    private var products: List<Data>? = null

    fun search(){
        view?.showLoading()

        repository.getProducts(object : RepositoryCallback<List<Data>>{
            override fun onError() {
                view?.showError()
            }

            override fun onSuccess(products: List<Data>?) {
                this@HomePresenter.products = products
                if (products != null && products.isNotEmpty()){
                    view?.showProducts(products)
                } else {
                    view?.showEmptyProducts()
                }
            }
        })
    }

    interface View {
        fun showLoading()
        fun showProducts(products: List<Data>)
        fun showEmptyProducts()
        fun showError()
    }
}