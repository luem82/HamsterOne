package com.example.hamsterone.activiries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.hamsterone.R
import com.example.hamsterone.adapters.ViewPagerAdapter
import com.example.hamsterone.fragments.other.GalleryFragment
import com.example.hamsterone.fragments.video.ChildVideoFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    lateinit var title: String
    lateinit var hrefPhoto: String
    lateinit var hrefVideo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initInfoSearch()
        innitViewPager(hrefPhoto, hrefVideo)

    }

    private fun innitViewPager(hrefPhoto: String, hrefVideo: String) {
        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(
            ChildVideoFragment.newInstance(hrefVideo), "Videos"
        )
        viewPagerAdapter.addFragment(
            GalleryFragment.newInstance(hrefPhoto, true), "Galleries"
        )
        
        viewPagerSearch.adapter = viewPagerAdapter
        val limit = (if (viewPagerAdapter.count > 1) viewPagerAdapter.count - 1 else 1)
        viewPagerSearch.offscreenPageLimit = limit
        tabLayoutSearch.setViewPager(viewPagerSearch)
    }

    private fun initInfoSearch() {
        title = intent.getStringExtra("title")
        hrefPhoto = intent.getStringExtra("hrefphoto")
        hrefVideo = intent.getStringExtra("hrefvideo")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
