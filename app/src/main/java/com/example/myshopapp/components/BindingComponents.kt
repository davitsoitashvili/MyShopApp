package com.example.myshopapp.components

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myshopapp.App

object BindingComponents {
    @JvmStatic
    @BindingAdapter("setPicture")
    fun setImage(imageView: ImageView, imageUrl : String) {
        Glide.with(App.getAppInstance().applicationContext).load(imageUrl).into(imageView)
    }
}