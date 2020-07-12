package com.htw.htwShopLive

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.htw.htwShopLive.mainclasses.Productsdatabase
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.product_details.*

class Productavailable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)

        val title = intent.getStringExtra("title")

        val product = Productsdatabase().getProductByName(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    product_name.text = it.title
                    Picasso.get().load(it.photoUrl).into(photo)
                    thePriceOfProduct.text = "$${it.price}"
                }, {})

        availability.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage("checked, $title is available!")
                    .setPositiveButton("OK") { p0, p1 ->

                    }
                    .create()
                    .show()
        }
    }
}