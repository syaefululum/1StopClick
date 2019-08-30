package com.cdc.a1stopclick.api

import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Product
import com.cdc.a1stopclick.models.RepositoryCallback
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GuestProductRetriever {
    private val service: OneStopClickServices

    companion object {
        const val BASE_URL = "https://goosc.herokuapp.com"
        const val size: Int = 10
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(OneStopClickServices::class.java)
    }
}