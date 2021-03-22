package com.example.hamsterone.fragments.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hamsterone.R
import com.example.hamsterone.activiries.LockActivity.Companion.actorRoom
import com.example.hamsterone.activiries.LockActivity.Companion.galleryRoom
import com.example.hamsterone.activiries.LockActivity.Companion.videoRoom
import com.example.hamsterone.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewPagerAdapter = ViewPagerAdapter(activity!!.supportFragmentManager)
        viewPagerAdapter.addFragment(
            ChildBookmarkFragment.newInstance(
                "video", videoRoom!!.getVideoDAO().getAllVideos()
            ), "Videos"
        )

        viewPagerAdapter.addFragment(
            ChildBookmarkFragment.newInstance(
                "actor", actorRoom!!.getActorDAO().getAllActor()
            ), "Actors"
        )

        viewPagerAdapter.addFragment(
            ChildBookmarkFragment.newInstance(
                "gallery", galleryRoom!!.getGalleryDAO().getAllGalleries()
            ), "Galleries"
        )

        viewPagerBookmark.adapter = viewPagerAdapter
        var limit = (if (viewPagerAdapter.count > 1) viewPagerAdapter.count - 1 else 1)
        viewPagerBookmark.offscreenPageLimit = limit
        tabLayoutBookmark.setViewPager(viewPagerBookmark)
    }
}