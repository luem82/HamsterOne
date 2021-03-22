package com.example.hamsterone.activiries

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.hamsterone.R
import com.example.hamsterone.adapters.ViewPagerAdapter
import com.example.hamsterone.fragments.other.GalleryFragment
import com.example.hamsterone.fragments.video.ChildVideoFragment
import com.example.hamsterone.models.Actor
import kotlinx.android.synthetic.main.activity_detail_actor.*

class DetailActorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_actor)

        var actor = intent.getSerializableExtra("actor") as Actor
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Actor: ${actor.name}"

        var videoHref = actor.href
        var photoHref = "https://xhamster.one/photos/search/${actor.name.replace(" ", "+")}"
        initViewPager(videoHref, photoHref)
    }

    private fun initViewPager(videoHref: String, photoHref: String) {

        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(
            ChildVideoFragment.newInstance(videoHref), "Videos"
        )
        viewPagerAdapter.addFragment(
            GalleryFragment.newInstance(photoHref, true), "Galleries"
        )

        viewPagerDetailActor.adapter = viewPagerAdapter
        val limit = (if (viewPagerAdapter.count > 1) viewPagerAdapter.count - 1 else 1)
        viewPagerDetailActor.offscreenPageLimit = limit
        tabLayoutDetailActor.setViewPager(viewPagerDetailActor)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.face_in, R.anim.slide_out_right)
    }
}
