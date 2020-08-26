package com.example.myshopapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myshopapp.database.tables.Shoes
import com.example.myshopapp.database.tables.Watches

@Dao
interface WatchesDao {
    @Query("SELECT * FROM watches")
    fun getAllWatches(): List<Shoes>

    @Insert
    fun insertAllWatches(vararg watches: Watches)

    @Query("DELETE FROM watches")
    fun deleteWatches()
}