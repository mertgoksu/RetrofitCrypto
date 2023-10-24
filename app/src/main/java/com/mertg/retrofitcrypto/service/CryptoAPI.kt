package com.mertg.retrofitcrypto.service

import com.mertg.retrofitcrypto.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query

interface CryptoAPI {
    //GET, POST, UPDATE, DELETE
//    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    @GET("bf08f366-afb5-4680-84b0-9d23eb263069")
    fun getData() : Observable<List<CryptoModel>>

//    fun getData() : Call<List<CryptoModel>>


}