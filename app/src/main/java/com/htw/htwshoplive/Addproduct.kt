package com.htw.htwShopLive

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.htw.htwShopLive.database.AppDatabase
import com.htw.htwShopLive.database.ProductFromDatabase
import kotlinx.android.synthetic.main.addtodatabase.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Addproduct : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.addtodatabase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitButton.setOnClickListener {
            val title = productTitle.text
            d("htw", "activated :) with text of $title")

            doAsync {

                val db = Room.databaseBuilder(
                        activity!!.applicationContext,
                        AppDatabase::class.java, "database-name"
                ).build()

                db.productDao().insertAll(ProductFromDatabase(null, title.toString(), 12.34))

                uiThread {
                    d("htw", "added to home")
                }
            }
        }
    }
}
