package com.example.myshopapp.network

import com.example.myshopapp.models.BagModel
import com.example.myshopapp.models.ShoesModel
import com.example.myshopapp.models.WatchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResponseLoader {

    fun shoesResponse(shoesCallback: (MutableList<ShoesModel>?, errorMessage: String?) -> Unit) {
        shopService.getShoesResponse().enqueue(object : Callback<MutableList<ShoesModel>> {
            override fun onFailure(call: Call<MutableList<ShoesModel>>, t: Throwable) {
                shoesCallback.invoke(null, checkErrorMessage(t.message.toString()))
            }

            override fun onResponse(
                call: Call<MutableList<ShoesModel>>,
                response: Response<MutableList<ShoesModel>>
            ) {
                shoesCallback.invoke(response.body(), null)
            }
        })
    }

    fun bagsResponse(bagsCallback: (MutableList<BagModel>?, errorMessage: String?) -> Unit) {
        shopService.getBagsResponse().enqueue(object : Callback<MutableList<BagModel>> {
            override fun onFailure(call: Call<MutableList<BagModel>>, t: Throwable) {
                bagsCallback(null, checkErrorMessage(t.message.toString()))
            }

            override fun onResponse(
                call: Call<MutableList<BagModel>>,
                response: Response<MutableList<BagModel>>
            ) {
                bagsCallback.invoke(response.body(), null)
            }

        })
    }

    fun watchesResponse(watchCallback: (MutableList<WatchModel>?, errorMessage: String?) -> Unit) {
        shopService.getWatchesResponse().enqueue(object : Callback<MutableList<WatchModel>> {
            override fun onFailure(call: Call<MutableList<WatchModel>>, t: Throwable) {
                watchCallback.invoke(null, checkErrorMessage(t.message.toString()))
            }

            override fun onResponse(
                call: Call<MutableList<WatchModel>>,
                response: Response<MutableList<WatchModel>>
            ) {
                watchCallback.invoke(response.body(), null)
            }

        })
    }

    private fun checkErrorMessage(response: String): String {
        return if (response == "Unable to resolve host \"myshopservice.herokuapp.com\": No address associated with hostname") {
            "No internet connection"
        } else {
            response
        }
    }
}

