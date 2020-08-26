package com.example.myshopapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myshopapp.database.tables.Bags
import com.example.myshopapp.database.tables.Shoes

@Dao
interface BagsDao {
    @Query("SELECT * FROM bags")
    fun getAllBags(): List<Shoes>

    @Insert
    fun insertAllBags(vararg bags: Bags)

    @Query("DELETE FROM bags")
    fun deleteBags()
}