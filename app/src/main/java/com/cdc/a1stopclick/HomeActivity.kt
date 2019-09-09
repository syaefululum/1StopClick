package com.cdc.a1stopclick

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import com.cdc.a1stopclick.adapters.ProductAdapter
import com.cdc.a1stopclick.models.Data
import com.cdc.a1stopclick.models.ProductRepository
import com.cdc.a1stopclick.models.ProductRepositoryImpl
import com.cdc.a1stopclick.presenter.HomePresenter
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    HomePresenter.View {
    private val presenter: HomePresenter by lazy { HomePresenter(ProductRepositoryImpl.getRepository(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //navigation init
        initToolbarNavigation()

        //set home view
        presenter.attachView(this)

        //load product list
        presenter.searchWithKeyword()

        //retry load product list
        retry.setOnClickListener { presenter.searchWithKeyword() }
        swipeToRefresh.setOnRefreshListener {
            presenter.pullRefresh()
            presenter.searchWithKeyword()
            swipeToRefresh.isRefreshing = false

        }
    }

    override fun showLoading() {
        loadingContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorContainer.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
    }

    override fun showProducts(products: ArrayList<Data>) {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        setupProductList(products)
    }

    fun setupProductList(products: ArrayList<Data>) {
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = ProductAdapter(products)
    }

    override fun showEmptyProducts() {
        noresultsContainer.visibility = View.VISIBLE
        loadingContainer.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorContainer.visibility = View.GONE
    }

    override fun showError() {
        loadingContainer.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        noresultsContainer.visibility = View.GONE
    }

    fun initToolbarNavigation() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
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

        val searchItem: MenuItem = menu.findItem(R.id.action_settings)
        val searchView: SearchView = searchItem.actionView as SearchView
        getProductList(searchView, searchItem)

        return super.onCreateOptionsMenu(menu)
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

    private fun getProductList(searchView: SearchView, searchItem: MenuItem) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    return false
                }
                val query = query.toString()
                presenter.setKeyword(query)
                presenter.resetPage()
                presenter.resetProducts()
                presenter.searchWithKeyword()
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                presenter.resetPage()
                presenter.resetKeyword()
                presenter.resetProducts()
                presenter.searchWithKeyword()
                return true
            }
        })
    }
}
