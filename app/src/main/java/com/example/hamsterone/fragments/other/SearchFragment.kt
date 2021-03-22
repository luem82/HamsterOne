package com.example.hamsterone.fragments.other


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.hamsterone.R
import com.example.hamsterone.adapters.GalleryAdapter
import com.example.hamsterone.recyclerviewplayvideo.VideoPlayerAdapter
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.models.Video
import com.example.hamsterone.recyclerviewplayvideo.VerticalSpacingItemDecorator
import com.example.hamsterone.recyclerviewplayvideo.VideoPlayerRecyclerView
import com.example.hamsterone.utils.FetchData
import com.example.hamsterone.utils.Helpers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var type = ""
    lateinit var rvSearchVideo: VideoPlayerRecyclerView
    lateinit var rvSearchPhoto: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSearchPhoto = view.findViewById(R.id.rvSearchPhoto)
        rvSearchVideo = view.findViewById(R.id.rvSearchVideo)

        Helpers.keyboardUtil(context!!, edt_search, true)

        initSpinner()

        iv_search.setOnClickListener {
            if (TextUtils.isEmpty(edt_search.text.toString())) {
                Toast.makeText(context, "Please enter search key.", Toast.LENGTH_SHORT).show()
            } else {

                var keySearch = edt_search.text.toString().replace(" ", "+")

                when (type) {
                    "Video" -> {
                        rvSearchVideo.visibility = View.VISIBLE
                        rvSearchPhoto.visibility = View.GONE
                        var href = "https://xhamster.one/search/${keySearch}"
                        searchVideo(href)
                    }
                    else -> {
                        rvSearchVideo.visibility = View.GONE
                        rvSearchPhoto.visibility = View.VISIBLE
                        var href = "https://xhamster.one/photos/search/${keySearch}"
                        searchPhoto(href)
                    }
                }
                pb_search.visibility = View.VISIBLE
                Helpers.keyboardUtil(context!!, it, false)
            }
        }
    }

    private fun searchVideo(href: String) {

        var list = mutableListOf<Video>()
        var linearLayoutManager = LinearLayoutManager(context)
        rvSearchVideo.layoutManager = linearLayoutManager
        val itemDecorator = VerticalSpacingItemDecorator(10)
        rvSearchVideo.addItemDecoration(itemDecorator)
        var videoPlayerAdapter =
            VideoPlayerAdapter(list, initGlide())
        rvSearchVideo.adapter = videoPlayerAdapter

        if (!list.isNullOrEmpty()) {
            list.clear()
            rvSearchVideo.clearMediaObjects()
            rvSearchVideo.removeAllViews()
            videoPlayerAdapter.notifyDataSetChanged()
        }

        CoroutineScope(Dispatchers.Main).launch {
            list.addAll(FetchData.getVideos(href))
            rvSearchVideo.setMediaObjects(list as ArrayList<Video>?)
            videoPlayerAdapter.notifyDataSetChanged()
            pb_search.visibility = View.GONE
        }
    }

    private fun searchPhoto(href: String) {

        var list = mutableListOf<Gallery>()
        var gridLayoutManager = GridLayoutManager(context, 2)
        var galleryAdapter = GalleryAdapter(list)
        rvSearchPhoto.layoutManager = gridLayoutManager
        rvSearchPhoto.adapter = galleryAdapter

        if (!list.isNullOrEmpty()) {
            list.clear()
            rvSearchPhoto.removeAllViews()
            galleryAdapter.notifyDataSetChanged()
        }

        CoroutineScope(Dispatchers.Main).launch {
            list.addAll(FetchData.getGalleries(href))
            galleryAdapter.notifyDataSetChanged()
            pb_search.visibility = View.GONE
        }
    }

    private fun initSpinner() {
        var arrayAdapter =
            ArrayAdapter.createFromResource(
                context!!, R.array.TypeSearch,
                android.R.layout.simple_spinner_item
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                type = parent!!.getItemAtPosition(0).toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                type = parent!!.getItemAtPosition(position).toString()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        Helpers.keyboardUtil(context!!, iv_search, false)
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    override fun onDestroyView() {
        if (rvSearchVideo != null)
            rvSearchVideo.releasePlayer()
        super.onDestroyView()
    }

}
