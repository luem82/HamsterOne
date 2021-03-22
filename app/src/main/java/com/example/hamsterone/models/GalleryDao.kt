package com.example.hamsterone.models

import androidx.room.*

@Dao
interface GalleryDao {

    @Query("SELECT * FROM ROOM_TABLE_GALLERY")
    fun getAllGalleries(): MutableList<Gallery>

    @Insert
    fun addGallery(gallery: Gallery)

    @Update
    fun updateGallery(gallery: Gallery)

    @Delete
    fun deleteGallery(gallery: Gallery)
}