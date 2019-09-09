package com.cdc.a1stopclick.models

import com.cdc.a1stopclick.models.ActivityRepository.*

class ActivityModel: Model {
    private var currentPage = 0
    private var size = 10
    private var produts: ArrayList<Data> = arrayListOf()
    private var searchKeywoard = ""

    override fun getCurrentPage() = currentPage
    override fun getSize()= size
    override fun incrementPage() {
        currentPage++
    }

    override fun incrementProduct(newProduct: ArrayList<Data>?) {
        if (newProduct != null) {
            produts.addAll(0,newProduct)
        }
    }

    override fun getCurrentProducts(): ArrayList<Data> = produts
    override fun resetCurrentPage() {
        currentPage = 0
    }

    override fun getSearchKeyword(): String = searchKeywoard
    override fun resetSearchKeyword() {
        searchKeywoard = ""
    }

    override fun setSearchKeyword(searchKeyword: String) {
        searchKeywoard = searchKeyword
    }

    override fun resetProducts() {
        produts = arrayListOf()
    }
}