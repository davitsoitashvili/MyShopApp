package com.example.myshopapp.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "bags")
data class Bags(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url_one") val imageUrlOne: String?,
    @ColumnInfo(name = "image_url_two") val imageUrlTwo: String?,
    @ColumnInfo(name = "image_url_three ") val imageUrlThree: String?,
    @ColumnInfo(name = "price") val price: Int?,
    @ColumnInfo(name = "time") val time: String
)