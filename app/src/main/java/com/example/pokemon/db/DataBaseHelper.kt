package com.example.pokemon.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScoreDataClass::class], version = 1)
abstract class DataBaseHelper : RoomDatabase() {
    abstract fun dao(): DataBaseInterFace
}