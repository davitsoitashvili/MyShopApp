package com.example.myshopapp

import android.app.Application

class App : Application() {

    companion object {
        var app: App? = null
        fun getAppInstance(): App {
            return app!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this

    }


}