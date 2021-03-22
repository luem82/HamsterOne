package com.example.hamsterone.models

import androidx.room.*

@Dao
interface VideoDao {

    @Query("SELECT * FROM ROOM_TABLE_VIDEO")
    fun getAllVideos(): MutableList<Video>

    @Insert
    fun addVideo(video: Video)

    @Update
    fun updateVideo(video: Video)

    @Delete
    fun deleteVideo(video: Video)
}