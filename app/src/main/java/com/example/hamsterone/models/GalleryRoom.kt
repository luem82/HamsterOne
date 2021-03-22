package com.example.hamsterone.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Gallery::class), version = 1)
abstract class GalleryRoom : RoomDatabase() {
    abstract fun getGalleryDAO(): GalleryDao
}