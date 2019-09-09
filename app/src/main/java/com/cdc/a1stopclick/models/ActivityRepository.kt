package com.cdc.a1stopclick.models

interface ActivityRepository {
    interface Model {
        //pagination handler
        fun getCurrentPage(): Int
        fun getSize(): Int
        fun incrementPage()
        fun resetCurrentPage()

        //product handler
        fun incrementProduct(newProduct: ArrayList<Data>?)
        fun getCurrentProducts(): ArrayList<Data>
        fun resetProducts()

        //search handler
        fun setSearchKeyword(searchKeyword: String)
        fun getSearchKeyword(): String
        fun resetSearchKeyword()
    }
}