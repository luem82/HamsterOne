package com.example.hamsterone.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.hamsterone.activiries.LockActivity
import com.example.hamsterone.activiries.LockActivity.Companion.actorRoom
import com.example.hamsterone.activiries.LockActivity.Companion.galleryRoom
import com.example.hamsterone.activiries.LockActivity.Companion.videoRoom
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.models.MyPhoto
import com.example.hamsterone.models.Video

object Helpers {

    fun downloadPhoto(myPhoto: MyPhoto, context: Context) {
        var downloadUri = Uri.parse(myPhoto.url)
        var fileName = "Photo_${myPhoto.id}.${myPhoto.type}"

        val downloadRequest = DownloadManager.Request(downloadUri)
            .setTitle(fileName)
            .setDescription("Downloading Photo...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(downloadRequest)
        Toast.makeText(context, "Downloading Photo.", Toast.LENGTH_SHORT).show()
    }

    fun actorBookmark(actor: Actor, isAdd: Boolean) {
        var actorDao = actorRoom!!.getActorDAO()
        if (isAdd) {
            actorDao.addActor(actor)
        } else {
            actorDao.deleteActor(actor)
        }
    }

    fun galleryBookmark(gallery: Gallery, isAdd: Boolean) {
        var galleryDao = galleryRoom!!.getGalleryDAO()
        if (isAdd) {
            galleryDao.addGallery(gallery)
        } else {
            galleryDao.deleteGallery(gallery)
        }
    }

    fun videoBookmark(video: Video, isAdd: Boolean) {
        var videoDao = videoRoom!!.getVideoDAO()
        if (isAdd) {
            videoDao.addVideo(video)
        } else {
            videoDao.deleteVideo(video)
        }
    }

    fun keyboardUtil(context: Context, view: View, isShow: Boolean) {
        var imm =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isShow) {
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } else {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}