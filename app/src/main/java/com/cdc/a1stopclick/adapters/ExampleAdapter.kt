package com.cdc.a1stopclick.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cdc.a1stopclick.R
import com.cdc.a1stopclick.models.Example

@Suppress("UNREACHABLE_CODE")
class ExampleAdapter(val userList: ArrayList<Example>) : RecyclerView.Adapter<ExampleAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.product_list_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.productName?.text = userList[p1].name
        p0.price?.text = userList[p1].price.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productName = itemView.findViewById<TextView>(R.id.tvProductName)
        val price = itemView.findViewById<TextView>(R.id.tvProductPrice)
    }
}