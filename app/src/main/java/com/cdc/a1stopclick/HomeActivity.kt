package com.cdc.a1stopclick

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.cdc.a1stopclick.adapters.ProductAdapter
import com.cdc.a1stopclick.api.GuestProductRetriever
import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.Preview
import com.cdc.a1stopclick.models.Product
import com.cdc.a1stopclick.models.ProductRepositoryImpl
import com.cdc.a1stopclick.presenter.HomePresenter
import kotlinx.android.synthetic.main.content_home.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val productRetriever = GuestProductRetriever()
    private val presenter: HomePresenter by lazy { HomePresenter(ProductRepositoryImpl.getRepository(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        //start
        presenter.search()

        //end

        val dataList = ArrayList<Data>()
        dataList.add(Data("NJACKONJ", "test", Preview("", false), 1234556, "",false, "https://goosc.herokuapp.com/static/images/2Fast2Furious.jpg" ))
        dataList.add(Data("PNTCWEIE", "drama kroya", Preview("", false), 250, "",false, "https://goosc.herokuapp.com/static/images/JohnWick.jpg" ))
        dataList.add(Data("SHTLJGGI", "Silent Comedy", Preview("", false), 10000000, "",false, "https://goosc.herokuapp.com/static/images/QYOCDXBX.jpg" ))
        dataList.add(Data("IUGXVXFB", "Naruto the movie", Preview("", false), 10000000, "",false, "https://images-na.ssl-images-amazon.com/images/I/71rNJQ2g-EL._SY606_.jpg" ))

        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        //recyclerView.adapter = ProductAdapter(dataList)

        if (isNetworkConnected()) {
            productRetriever.getProductList(callback, 0,GuestProductRetriever.size)
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager // 1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }

    private val callback = object : retrofit2.Callback<Product> {
        override fun onFailure(call: Call<Product>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling Github API", t)
        }

        override fun onResponse(call: Call<Product>?, response: Response<Product>?) {
            response?.isSuccessful.let {
                val resultList = Product(response?.body()?.data, response?.body()?.code,
                    response?.body()?.length, response?.body()?.message
                )
                Log.e("MainActivity", resultList.toString())
                recyclerView.adapter = ProductAdapter(resultList.data as ArrayList<Data>)
            }
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_cart -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}