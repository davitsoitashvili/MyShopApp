package com.example.myshopapp.network

import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.models.WatchModel
import retrofit2.http.GET

interface ShopService {
    @GET("/")
    fun getShoesResponse() : retrofit2.Call<MutableList<ShoesModel>>

    @GET("/bags")
    fun getBagsResponse() : retrofit2.Call<MutableList<BagModel>>

    @GET("/watches")
    fun getWatchesResponse() :  retrofit2.Call<MutableList<WatchModel>>
}