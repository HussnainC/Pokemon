package com.example.pokemon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataBaseInterFace {
    @Query("Select * from scoretbl")
    fun getScore(): List<ScoreDataClass>

    @Insert
    fun insert(value: ScoreDataClass): Long

}