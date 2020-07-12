package com.htw.htwShopLive.model

import com.google.gson.annotations.SerializedName

data class Product(
        @SerializedName("name")
        val title: String,

        @SerializedName("photo_url")
        val photoUrl: String,

        @SerializedName("price")
        val price: Double,

        @SerializedName("isOnSale")
        val isOnSale: Boolean

//description to be done
)