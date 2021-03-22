package com.example.hamsterone.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Video::class), version = 1)
abstract class VideoRoom : RoomDatabase() {
    abstract fun getVideoDAO(): VideoDao
}