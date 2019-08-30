package com.cdc.a1stopclick.models

data class Product(
    val data: ArrayList<Data>?,
    val code: Int?,
    val length: Int?,
    val message: String?
)

data class Data(
    val id: String,
    val name: String,
    val preview: Preview,
    val price: Int,
    val promote_title: String,
    val promoted_product: Boolean,
    val thumbnail: String
)

data class Preview(
    val String: String,
    val Valid: Boolean
)