package com.example.hamsterone.fragments.bookmark


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.hamsterone.R
import com.example.hamsterone.adapters.ActorAdapter
import com.example.hamsterone.adapters.BookmarkAdapter
import com.example.hamsterone.adapters.GalleryAdapter
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.models.Video
import kotlinx.android.synthetic.main.fragment_child_bookmark.*
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChildBookmarkFragment : Fragment() {

    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_PARAM1)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String, list: Any) =
            ChildBookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, type)
                    putSerializable(ARG_PARAM2, list as Serializable)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var gridLayoutManager = GridLayoutManager(context, 2)
        rvBookmark.layoutManager = gridLayoutManager

        when (type) {
            "video" -> {
                var list = arguments!!.getSerializable(ARG_PARAM2) as MutableList<Video>
                var bookmarkAdapter =
                    BookmarkAdapter("video", list.toMutableList(), tvEmpty, activity!!)
                rvBookmark.adapter = bookmarkAdapter

                if (list.isNullOrEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                } else {
                    tvEmpty.visibility = View.GONE
                }
            }
            "actor" -> {
                var list = arguments!!.getSerializable(ARG_PARAM2) as MutableList<Actor>
                var bookmarkAdapter =
                    BookmarkAdapter("actor", list.toMutableList(), tvEmpty, activity!!)
                rvBookmark.adapter = bookmarkAdapter

                if (list.isNullOrEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                } else {
                    tvEmpty.visibility = View.GONE
                }
            }
            "gallery" -> {
                var list = arguments!!.getSerializable(ARG_PARAM2) as MutableList<Gallery>
                var bookmarkAdapter =
                    BookmarkAdapter("gallery", list.toMutableList(), tvEmpty, activity!!)
                rvBookmark.adapter = bookmarkAdapter

                if (list.isNullOrEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                } else {
                    tvEmpty.visibility = View.GONE
                }
            }
        }
    }

}
