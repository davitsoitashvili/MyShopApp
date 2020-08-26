package com.example.myshopapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myshopapp.database.tables.Shoes
import com.example.myshopapp.models.ShoesModel

@Dao
interface ShoesDao {
    @Query("SELECT * FROM shoes")
    fun getAllShoes(): List<Shoes>

    @Insert
    fun insertAllShoes(vararg shoes: Shoes)

    @Query("DELETE FROM shoes")
    fun deleteShoes()
}