package com.example.peee.sampletodo.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.peee.sampletodo.R

/**
 * A fragment that holds fragment for all to-do items.
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
