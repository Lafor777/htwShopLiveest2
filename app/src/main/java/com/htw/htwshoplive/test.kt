package com.htw.htwShopLive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class test : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.test, container, false)
    }
}