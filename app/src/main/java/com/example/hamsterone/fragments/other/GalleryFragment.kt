package com.example.hamsterone.fragments.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hamsterone.R
import com.example.hamsterone.adapters.ActorAdapter
import com.example.hamsterone.adapters.GalleryAdapter
import com.example.hamsterone.fragments.actor.ChildActorFragment
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.utils.Consts
import com.example.hamsterone.utils.FetchData
import kotlinx.android.synthetic.main.fragment_child_actor.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    lateinit var galleryList: MutableList<Gallery>
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var gridlayoutManager: GridLayoutManager
    private var isLoading: Boolean = false
    private var visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var page = 1

    private var urlSearch: String? = null
    private var isSearch: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(url: String, search: Boolean) =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putString("url", url)
                    putBoolean("search", search)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlSearch = it.getString("url")
            isSearch = it.getBoolean("search")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fetchData()
    }

    private fun initRecyclerView() {
        galleryList = mutableListOf()
        galleryAdapter = GalleryAdapter(galleryList)
        gridlayoutManager = GridLayoutManager(context, 2)
        gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            //Each item occupies 1 span by default.
            override fun getSpanSize(p0: Int): Int {
                return when (galleryAdapter.getItemViewType(p0)) {
                    //returns total no of spans, to occupy full sapce for progressbar
                    GalleryAdapter.TYPE_LOADING -> gridlayoutManager.spanCount
                    GalleryAdapter.TYPE_GALLERY -> 1
                    else -> -1
                }
            }
        }
        rvGallery.apply {
            layoutManager = gridlayoutManager
            adapter = galleryAdapter
            addOnScrollListener(scrollListener)
        }
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //total no. of items
            totalItemCount = gridlayoutManager.itemCount
            //last visible item position
            lastVisibleItem = gridlayoutManager.findLastCompletelyVisibleItemPosition()
            if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                if (!isSearch) {
                    loadMore()
                    isLoading = true
                }
            }
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            if (isSearch) {
                galleryList.addAll(FetchData.getGalleries(urlSearch!!))
            } else {
                galleryList.addAll(FetchData.getGalleries("${Consts.URL_PHOTO}/${page}"))
            }
            galleryAdapter.notifyDataSetChanged()
            pb_gallery.visibility = View.GONE
        }
    }

    private fun loadMore() {
        page++
        if (page <= 100) {
            //add null->load item
            galleryList.add(Consts.NULL_GALLERY)
            galleryAdapter.notifyItemInserted(galleryList.size - 1)

            var more = mutableListOf<Gallery>()
            CoroutineScope(Dispatchers.Main).launch {
                more = FetchData.getGalleries("${Consts.URL_PHOTO}/${page}")
            }

            rvGallery.postDelayed(Runnable {
                //removes load item in list.
                galleryList.removeAt(galleryList.size - 1)
                galleryAdapter.notifyItemRemoved(galleryList.size)
                galleryList.addAll(more)
                galleryAdapter.notifyDataSetChanged()
                isLoading = false
            }, 2000)

        } else {
            Toast.makeText(context, "End of list.", Toast.LENGTH_SHORT).show()
        }
    }

}