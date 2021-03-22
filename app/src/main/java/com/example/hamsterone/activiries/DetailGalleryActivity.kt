package com.example.hamsterone.activiries

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hamsterone.R
import com.example.hamsterone.adapters.PhotoAdapter
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.models.MyPhoto
import com.example.hamsterone.utils.FetchData
import kotlinx.android.synthetic.main.activity_detail_gallery.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailGalleryActivity : AppCompatActivity() {

    private lateinit var list: MutableList<MyPhoto>
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gallery)

        var gallery = intent.getSerializableExtra("gallery") as Gallery

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = gallery.title

        list = mutableListOf()
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        photoAdapter = PhotoAdapter(list, 1, null)
        rvPhoto.layoutManager = staggeredGridLayoutManager
        rvPhoto.adapter = photoAdapter

        CoroutineScope(Dispatchers.Main).launch {
            list.addAll(FetchData.getPhotos(gallery.href))
            photoAdapter.notifyDataSetChanged()
            pbPhoto.visibility = View.GONE
        }

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

