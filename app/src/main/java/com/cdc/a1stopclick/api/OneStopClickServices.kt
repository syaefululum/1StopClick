package com.cdc.a1stopclick.api

import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OneStopClickServices {
    @GET("/guest/product")
    fun retrieveGuestProductList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Product>
}