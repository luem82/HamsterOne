package com.example.hamsterone.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "ROOM_TABLE_GALLERY")
class Gallery : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Long

    @ColumnInfo(name = "title")
    var title: String

    @ColumnInfo(name = "views")
    var views: String

    @ColumnInfo(name = "photoCount")
    var photoCount: String

    @ColumnInfo(name = "thumb")
    var thumb: String

    @ColumnInfo(name = "href")
    var href: String

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean

    constructor(
        id: Long, title: String, views: String,
        photoCount: String, thumb: String, href: String, isFavorite: Boolean
    ) {
        this.id = id
        this.title = title
        this.views = views
        this.photoCount = photoCount
        this.thumb = thumb
        this.href = href
        this.isFavorite = isFavorite
    }
}