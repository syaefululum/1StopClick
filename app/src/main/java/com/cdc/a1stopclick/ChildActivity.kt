package com.cdc.a1stopclick

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class ChildActivity : AppCompatActivity() {
    fun OnPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}