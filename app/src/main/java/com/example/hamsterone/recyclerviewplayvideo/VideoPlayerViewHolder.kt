package com.example.hamsterone.recyclerviewplayvideo

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hamsterone.R
import com.example.hamsterone.activiries.EmbedVideoActivity
import com.example.hamsterone.models.Video
import com.example.hamsterone.utils.Helpers
import com.google.android.material.snackbar.Snackbar


class VideoPlayerViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    var media_container: FrameLayout? = null
    var title: TextView? = null
    var review: TextView? = null
    var views: TextView? = null
    var like: TextView? = null
    var full: TextView? = null
    var bookmark: TextView? = null
    var thumbnail: ImageView? = null
    var progressBar: ProgressBar? = null
    var parent: View? = null
    var requestManager: RequestManager? = null

    init {
        parent = itemview
        media_container = itemview.findViewById(R.id.media_container)
        title = itemview.findViewById(R.id.tv_video_title)
        views = itemview.findViewById(R.id.tv_video_view)
        like = itemview.findViewById(R.id.tv_video_like)
        review = itemview.findViewById(R.id.tv_video_review)
        full = itemview.findViewById(R.id.tv_video_full)
        bookmark = itemview.findViewById(R.id.tv_video_bookmark)
        thumbnail = itemview.findViewById(R.id.iv_video_thumb)
        progressBar = itemview.findViewById(R.id.pb_video)
    }

    fun onBind(video: Video, requestManager: RequestManager) {
        this.requestManager = requestManager
        parent!!.setTag(this)
        title!!.text = video.title
        like!!.text = video.like
        views!!.text = video.view
        this.requestManager!!
//            .load(R.drawable.ic_bookmark_black_24dp)
            .load(video.thumb)
            .into(thumbnail!!)

        full!!.setOnClickListener {
            openFullVideo(it.context, video)
        }

        bookmark!!.setOnClickListener {
            openDialogAddBookmark(it.context, video)
        }
    }

    private fun openDialogAddBookmark(context: Context?, video: Video) {
        var builder = AlertDialog.Builder(context!!)
            .setIcon(R.drawable.ic_bookmark_black_24dp)
            .setTitle("Add to bookmark")
            .setMessage("Do you want add this video to bookmark list ?")
            .setNegativeButton("No", null)
            .setPositiveButton("Yes", (object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                    Helpers.videoBookmark(video, true)
                    Snackbar.make(media_container!!, "Added", Snackbar.LENGTH_SHORT).show()
                }

            }))

        var dialog = builder.create()
        dialog.show()
    }

    private fun openFullVideo(context: Context?, video: Video) {
        var intent = Intent(context, EmbedVideoActivity::class.java)
        intent.putExtra("embed", video.embed)
        context?.startActivity(intent)
        (context as AppCompatActivity).overridePendingTransition(R.anim.zoom_in, R.anim.face_out)
    }

}