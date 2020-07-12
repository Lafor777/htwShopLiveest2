package com.htw.htwShopLive

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.room.Room
import com.htw.htwShopLive.pay.paycorb
import com.htw.htwShopLive.database.AppDatabase
import com.htw.htwShopLive.database.ProductFromDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Mainmenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        doAsync {

            val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
            ).build()

            db.productDao().insertAll(ProductFromDatabase(null, "add", 1.99))
            val products = db.productDao().getAll()

            uiThread {

                d("htw", "products size? ${products.size} ${products[0].title}")

            }
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, MainHome())
                .commit()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, MainHome())
                            .commit()
                }
                R.id.actiononSale -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, test())
                            .commit()
                }
                R.id.actionCloseApp -> {
                    d("htw", "App is closing")
                }
                R.id.actionAdmin -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, Addproduct())
                            .commit()
                }
            }

                    //to be done
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actionCart) {
            d("htw", "going to cart")
            startActivity(Intent(this, paycorb::class.java))
            return true
        }
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
}
