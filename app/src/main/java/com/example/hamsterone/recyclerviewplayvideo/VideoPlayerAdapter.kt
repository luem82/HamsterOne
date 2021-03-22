package com.example.hamsterone.recyclerviewplayvideo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hamsterone.R
import com.example.hamsterone.models.Video
import com.example.hamsterone.recyclerviewplayvideo.VideoPlayerViewHolder

class VideoPlayerAdapter(
    var list: MutableList<Video>,
    var requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoPlayerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_player, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoPlayerViewHolder).onBind(list[position], requestManager)
    }

}