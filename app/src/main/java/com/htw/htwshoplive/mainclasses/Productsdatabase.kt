package com.htw.htwShopLive.mainclasses

import com.htw.htwShopLive.model.Product
import com.google.gson.Gson
import io.reactivex.Single
import java.net.URL

class Productsdatabase {

    fun getAllProducts(): Single<List<Product>> {
        return Single.create<List<Product>> {
            it.onSuccess(fetchProducts())
        }
    }

    fun searchForProducts(term: String): Single<List<Product>> {
        return Single.create<List<Product>> {
            val filteredProducts = fetchProducts().filter { it.title.contains(term, true) }
            it.onSuccess(filteredProducts)
        }
    }

    fun getProductByName(name: String): Single<Product> {
        return Single.create<Product> {
            val product = fetchProducts().first { it.title == name }
            it.onSuccess(product)
        }
    }

    fun fetchProducts(): List<Product> {
        val json = URL("https://gist.githubusercontent.com/Lafor777/2e02363ed327b0650cbf9131a6084e3e/raw/67199c6f2da313138968cf33c16b293fdc1bae15/shoping_products4.json").readText()
        return Gson().fromJson(json, Array<Product>::class.java).toList()
    }
}
