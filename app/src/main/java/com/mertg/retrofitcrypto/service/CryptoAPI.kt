package com.mertg.retrofitcrypto.service

import com.mertg.retrofitcrypto.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query

interface CryptoAPI {
    //GET, POST, UPDATE, DELETE
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1")
    fun getData() : Observable<List<CryptoModel>>

//    fun getData() : Call<List<CryptoModel>>


}