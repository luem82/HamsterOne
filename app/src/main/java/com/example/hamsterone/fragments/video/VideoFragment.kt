package com.example.hamsterone.fragments.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hamsterone.R
import com.example.hamsterone.adapters.ViewPagerAdapter
import com.example.hamsterone.utils.Consts
import com.example.hamsterone.utils.FetchData
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewPagerAdapter = ViewPagerAdapter(activity!!.supportFragmentManager)
        viewPagerAdapter.addFragment(
            ChildVideoFragment.newInstance(Consts.URL_VIDEO_NEWEST), "Newest"
        )
        viewPagerAdapter.addFragment(
            ChildVideoFragment.newInstance(Consts.URL_VIDEO_POPULAR), "Popular"
        )
        viewPagerAdapter.addFragment(
            ChildVideoFragment.newInstance(Consts.URL_VIDEO_BEST), "Best"
        )

        viewPagerVideo.adapter = viewPagerAdapter
        val limit = (if (viewPagerAdapter.count > 1) viewPagerAdapter.count - 1 else 1)
        viewPagerVideo.offscreenPageLimit = limit
        tabLayoutVideo.setViewPager(viewPagerVideo)
    }

}