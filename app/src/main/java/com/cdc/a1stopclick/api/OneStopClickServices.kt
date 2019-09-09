package com.cdc.a1stopclick.api

import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Product
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OneStopClickServices {

//    @GET("/guest/product")
//    fun retrieveGuestProductList(
//        @Query("page") page: Int?,
//        @Query("size") size: Int?
//    ): Call<Product>

    @GET("/guest/product/search")
    fun searchGuestProductList(
        @Query("q") q: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): Call<Product>

    companion object Factory {
        fun create(): OneStopClickServices {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://goosc.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(OneStopClickServices::class.java)
        }
    }
}