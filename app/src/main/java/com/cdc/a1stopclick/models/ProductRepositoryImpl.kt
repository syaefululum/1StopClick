package com.cdc.a1stopclick.models

import android.content.Context
import android.content.SharedPreferences
import com.cdc.a1stopclick.api.OneStopClickServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepositoryImpl(private val sharedPreferences: SharedPreferences) : ProductRepository {

    override fun getProducts(callback: RepositoryCallback<Product>, page: Int, size: Int) {
        val call = OneStopClickServices.create().retrieveGuestProductList(page, size)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response != null && response.isSuccessful){
                    if (response.body()?.code == 200){
                        callback.onSuccess(response.body()!!.data)
                    } else {
                        callback.onError()
                    }
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
               callback.onError()
            }
        })
    }

    companion object {
        fun getRepository(context: Context): ProductRepositoryImpl {
            return ProductRepositoryImpl(context.getSharedPreferences("Favorites", 0))
        }
    }

}