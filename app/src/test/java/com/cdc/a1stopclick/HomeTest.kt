package com.cdc.a1stopclick

import com.cdc.a1stopclick.models.*
import com.cdc.a1stopclick.presenter.HomePresenter
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class HomeTest {
    private lateinit var repository: ProductRepository
    private lateinit var presenter: HomePresenter
    private lateinit var view: HomePresenter.View

    @Before
    fun setup() {
        repository = mock()
        view = mock()
        presenter = HomePresenter(repository)
        presenter.attachView(view)
    }

    @Test
    fun home_callsShowLoading() {
        presenter.searchWithKeyword(null, 1, 10)
        verify(view).showLoading()
    }

    @Test
    fun home_withRepositoryHavingProducts_callsShowProducts() {
        val preview = Preview("String", false)
        val datas = ArrayList<Data>()
        datas.add(Data("id", "name", preview, 1, "promote_title", false, "thumbnail"))
        datas.add(Data("id", "name", preview, 1, "promote_title", false, "thumbnail"))

        val product = Product(datas, 200, 10, "message")


        doAnswer {
            val callback: RepositoryCallback<Product> = it.getArgument(0)
            callback.onSuccess(product.data)
        }.whenever(repository).getProducts(any(), eq(1), eq(10))

        presenter.searchWithKeyword(null, 1, 10)
        verify(view).showProducts(eq(product.data))
    }

    @Test
    fun home_withRepositoryNotHavingProducts_callsShowEmptyProducts() {
        val datas = ArrayList<Data>()
        val product = Product(datas, 200, 0, "message")

        doAnswer {
            val callback: RepositoryCallback<Product> = it.getArgument(0)
            callback.onSuccess(product.data)
        }.whenever(repository).getProducts(any(), eq(1), eq(10))

        presenter.searchWithKeyword(null, 1, 10)
        verify(view).showEmptyProducts()
    }

    @Test
    fun home_callsShowError() {
        val datas = ArrayList<Data>()
        val product = Product(datas, 500, 0, "message")

        doAnswer {
            val callback: RepositoryCallback<Product> = it.getArgument(0)
            callback.onError()
        }.whenever(repository).getProducts(any(), eq(1), eq(10))

        presenter.searchWithKeyword(null, 1, 10)
        verify(view).showError()
    }

    @Test
    fun home_callsInitNavigation() {

    }
}