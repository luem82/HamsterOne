package com.example.hamsterone.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ROOM_TABLE_VIDEO")
class Video : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Long

    @ColumnInfo(name = "idVideo")
    var idVideo: String

    @ColumnInfo(name = "title")
    var title: String

    @ColumnInfo(name = "view")
    var view: String

    @ColumnInfo(name = "like")
    var like: String

    @ColumnInfo(name = "thumb")
    var thumb: String

    @ColumnInfo(name = "preview")
    var preview: String

    @ColumnInfo(name = "href")
    var href: String

    @ColumnInfo(name = "embed")
    var embed: String

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean

    constructor(
        id: Long, idVideo: String, title: String,
        view: String, like: String, thumb: String,
        preview: String, embed: String, href: String, isFavorite: Boolean
    ) {
        this.id = id
        this.idVideo = idVideo
        this.title = title
        this.view = view
        this.like = like
        this.thumb = thumb
        this.preview = preview
        this.href = href
        this.embed = embed
        this.isFavorite = isFavorite
    }
}