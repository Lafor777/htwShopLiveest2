package com.htw.htwShopLive

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.htw.htwShopLive.model.Product
import com.htw.htwShopLive.mainclasses.Productsdatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.search.*
import kotlinx.android.synthetic.main.search.view.*

class MainHome : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.search, container, false)

        val categories = listOf("Hose", "Jacken", "Shirts", "Kleider", "Jeans")

        root.categoriesRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity, androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)
            adapter = Categories(categories)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsRepository = Productsdatabase().getAllProducts()
        loadRecyclerView(productsRepository)

        searchButton.setOnClickListener {
            loadRecyclerView(Productsdatabase().searchForProducts(searchTerm.text.toString()))
        }

    }

    fun loadRecyclerView(productsRepository: Single<List<Product>>) {
        val single = productsRepository
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    d("htw", "success :)")
                    recycler_view.apply {
                        layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 2)

                        adapter = ProductsAdapter(it) { extraTitle, extraImageUrl, photoView ->
                            val intent = Intent(activity, Productavailable::class.java)
                            intent.putExtra("title", extraTitle)
                            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as AppCompatActivity, photoView, "photoToAnimate")
                            startActivity(intent, options.toBundle())
                        }

                    }
                    progressBar.visibility = View.GONE
                }, {
                    d("htw", " error :( ${it.message}")
                })
    }
}