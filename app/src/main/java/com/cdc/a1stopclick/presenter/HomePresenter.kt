package com.cdc.a1stopclick.presenter

import com.cdc.a1stopclick.models.*
import com.cdc.a1stopclick.models.ActivityRepository.*

class HomePresenter(private val repository: ProductRepository) :
    BasePresenter<HomePresenter.View>() {
    private var products: ArrayList<Data>? = null
    private var model: Model = ActivityModel()

    fun pullRefresh(){
        model.incrementPage()
    }

    fun resetPage(){
        model.resetCurrentPage()
    }

    fun setKeyword(key: String){
        model.setSearchKeyword(key)
    }

    fun resetKeyword(){
        model.resetSearchKeyword()
    }

    fun resetProducts(){
        model.resetProducts()
    }

    fun searchWithKeyword() {
        view?.showLoading()
        repository.searchProducts(object : RepositoryCallback<Product> {
            override fun onError() {
                view?.showLoading()
            }

            override fun onSuccess(products: ArrayList<Data>?) {
                this@HomePresenter.products = products
                model.incrementProduct(products)
                if (model.getCurrentProducts() != null && model.getCurrentProducts().isNotEmpty()) {
                    view?.showProducts(model.getCurrentProducts())
                } else {
                    view?.showEmptyProducts()
                }
            }
        }, model.getSearchKeyword(), model.getCurrentPage(), model.getSize())
    }


    interface View {
        fun showLoading()
        fun showProducts(products: ArrayList<Data>)
        fun showEmptyProducts()
        fun showError()
    }
}