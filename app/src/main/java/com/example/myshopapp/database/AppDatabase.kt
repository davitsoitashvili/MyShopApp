package com.example.myshopapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshopapp.database.dao.BagsDao
import com.example.myshopapp.database.dao.ShoesDao
import com.example.myshopapp.database.dao.WatchesDao
import com.example.myshopapp.database.tables.Bags
import com.example.myshopapp.database.tables.Shoes
import com.example.myshopapp.database.tables.Watches

@Database(entities = [Shoes::class, Bags::class, Watches::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getShoes(): ShoesDao
    abstract fun getBags(): BagsDao
    abstract fun getWatches(): WatchesDao
}