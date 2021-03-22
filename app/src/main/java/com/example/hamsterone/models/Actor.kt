package com.example.hamsterone.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ROOM_TABLE_ACTOR")
class Actor : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Long

    @ColumnInfo(name = "name")
    var name: String

    @ColumnInfo(name = "views")
    var views: String

    @ColumnInfo(name = "videoCount")
    var videoCount: String

    @ColumnInfo(name = "thumb")
    var thumb: String

    @ColumnInfo(name = "href")
    var href: String

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean

    constructor(
        id: Long, name: String, views: String, videoCount: String,
        thumb: String, href: String, isFavorite: Boolean
    ) {
        this.id = id
        this.name = name
        this.views = views
        this.videoCount = videoCount
        this.thumb = thumb
        this.href = href
        this.isFavorite = isFavorite
    }
}