package com.example.hamsterone.fragments.video


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.hamsterone.recyclerviewplayvideo.VideoPlayerRecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.RequestManager
import com.example.hamsterone.R
import com.example.hamsterone.recyclerviewplayvideo.VideoPlayerAdapter
import com.example.hamsterone.models.Video
import com.example.hamsterone.utils.FetchData
import com.example.hamsterone.recyclerviewplayvideo.VerticalSpacingItemDecorator
import kotlinx.android.synthetic.main.fragment_child_video.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"

class ChildVideoFragment : Fragment() {

    private var urlGet: String? = null

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            ChildVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, url)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlGet = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_video, container, false)
    }

    lateinit var mRecyclerView: VideoPlayerRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view.findViewById(R.id.recycler_view)
        var linearLayoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = linearLayoutManager
        val itemDecorator = VerticalSpacingItemDecorator(10)
        mRecyclerView.addItemDecoration(itemDecorator)

        CoroutineScope(Dispatchers.Main).launch {
            var list = mutableListOf<Video>()
            list.addAll(FetchData.getVideos(urlGet!!))
            mRecyclerView.setMediaObjects(list as ArrayList<Video>?)
            var videoPlayerAdapter =
                VideoPlayerAdapter(list, initGlide())
            mRecyclerView.adapter = videoPlayerAdapter
            pbVideo.visibility = View.GONE
        }


    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    override fun onDestroyView() {
        if (mRecyclerView != null)
            mRecyclerView.releasePlayer()
        super.onDestroyView()
    }
}
