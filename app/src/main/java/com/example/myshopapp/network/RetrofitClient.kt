package com.example.myshopapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_PATH = "https://myshopservice.herokuapp.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_PATH)
    .build()

val shopService: ShopService = retrofit.create(ShopService::class.java)