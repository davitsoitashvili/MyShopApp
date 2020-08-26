package com.example.myshopapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoesModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("image_url_one")
    val imageUrlOne: String?,
    @SerializedName("image_url_two")
    val imageUrlTwo: String?,
    @SerializedName("image_url_three")
    val imageUrlThree: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("time")
    val time: String
) : Parcelable