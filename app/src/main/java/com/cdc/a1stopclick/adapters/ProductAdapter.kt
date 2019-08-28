package com.cdc.a1stopclick.adapters

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.cdc.a1stopclick.R
import com.cdc.a1stopclick.api.GuestProductRetriever
import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(val productList: ArrayList<Data>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.tvProductName)
        val price = itemView.findViewById<TextView>(R.id.tvProductPrice)
        val preview = itemView.findViewById<ImageView>(R.id.ivProductPreview)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.product_list_item, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.name?.text = productList[p1].name
        p0.price?.text = "Rp " + this.numberFormat(productList[p1].price).toString()
        Picasso.get().load(GuestProductRetriever.BASE_URL + productList[p1].thumbnail.toString()).error(R.drawable.missingimage2).placeholder(R.drawable.submitprogress).into(p0.preview)
    }

    fun numberFormat(price: Int): String {
        return NumberFormat.getInstance().format(price)
    }
}