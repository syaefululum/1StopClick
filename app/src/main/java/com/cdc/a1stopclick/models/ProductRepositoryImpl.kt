package com.cdc.a1stopclick.models

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class ProductRepositoryImpl(private val sharedPreferences: SharedPreferences) : ProductRepository {
    override fun getProducts(callback: RepositoryCallback<List<Data>>) {
        val callback = object : retrofit2.Callback<Product> {
            override fun onFailure(call: Call<Product>?, t: Throwable?) {
                Log.e("MainActivity", "Problem calling Github API", t)
                callback.onError()
            }

            override fun onResponse(call: Call<Product>?, response: Response<Product>?) {
                if (response != null && response.isSuccessful){
                    val product = response?.body()
                    if(product?.code == 200){
                        callback.onSuccess(product?.data)
                    } else {
                        callback.onError()
                    }
                }
                response?.isSuccessful.let {
                    val resultList = Product(response?.body()?.data, response?.body()?.code,
                        response?.body()?.length, response?.body()?.message
                    )
                }
            }
        }
    }

    companion object{
        fun getRepository(context: Context): ProductRepositoryImpl {
            return ProductRepositoryImpl(context.getSharedPreferences("Favorites", 0))
        }
    }

}