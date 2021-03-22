package com.example.hamsterone.models

import androidx.room.*

@Dao
interface ActorDao {

    @Query("SELECT * FROM ROOM_TABLE_ACTOR")
    fun getAllActor(): MutableList<Actor>

    @Insert
    fun addActor(actor: Actor)

    @Update
    fun updateActor(actor: Actor)

    @Delete
    fun deleteActor(actor: Actor)
}