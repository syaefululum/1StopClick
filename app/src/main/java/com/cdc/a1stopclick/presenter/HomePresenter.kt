package com.cdc.a1stopclick.presenter

import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Product
import com.cdc.a1stopclick.models.ProductRepository
import com.cdc.a1stopclick.models.RepositoryCallback

class HomePresenter(private val repository: ProductRepository): BasePresenter<HomePresenter.View>() {
    private var products: ArrayList<Data>? = null

    fun search(){
        view?.showLoading()

        repository.getProducts(object : RepositoryCallback<Product>{
            override fun onError() {
                view?.showError()
            }

            override fun onSuccess(t: ArrayList<Data>?) {
                this@HomePresenter.products = t
                if (products != null){
                    view?.showProducts(products!!)
                } else {
                    view?.showEmptyProducts()
                }

            }
        }, page = 0, size = 10)
    }

    interface View {
        fun showLoading()
        fun showProducts(products: ArrayList<Data>)
        fun showEmptyProducts()
        fun showError()
    }
}