package com.mertg.retrofitcrypto.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    //@SerializedName("currency")
    val name: String,
    //@SerializedName("price")
    val current_price: String,
    //@SerializedName("")
    val symbol: String



    )