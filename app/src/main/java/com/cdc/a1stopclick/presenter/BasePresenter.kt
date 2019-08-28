package com.cdc.a1stopclick.presenter

import android.view.View

abstract class BasePresenter<V> {
    protected var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}