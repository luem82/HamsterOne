package com.example.hamsterone.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Actor::class), version = 1)
abstract class ActorRoom : RoomDatabase() {
    abstract fun getActorDAO(): ActorDao
}