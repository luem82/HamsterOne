package com.example.hamsterone.fragments.actor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.hamsterone.R
import com.example.hamsterone.adapters.ActorAdapter
import com.example.hamsterone.models.Actor
import com.example.hamsterone.utils.Consts
import com.example.hamsterone.utils.FetchData
import kotlinx.android.synthetic.main.fragment_child_actor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChildActorFragment : Fragment() {

    private var urlGet: String? = null
    private var totalPage: Int? = null
    lateinit var actorList: MutableList<Actor>
    lateinit var actorAdapter: ActorAdapter
    lateinit var gridlayoutManager: GridLayoutManager
    private var isLoading: Boolean = false
    private var visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var page = 1

    companion object {
        @JvmStatic
        fun newInstance(url: String, page: Int) =
            ChildActorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, url)
                    putInt(ARG_PARAM2, page)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlGet = it.getString(ARG_PARAM1)
            totalPage = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_actor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fetchData()
    }

    private fun initRecyclerView() {
        actorList = mutableListOf()
        actorAdapter = ActorAdapter(actorList)
        gridlayoutManager = GridLayoutManager(context, 2)
        gridlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            //Each item occupies 1 span by default.
            override fun getSpanSize(p0: Int): Int {
                return when (actorAdapter.getItemViewType(p0)) {
                    //returns total no of spans, to occupy full sapce for progressbar
                    ActorAdapter.TYPE_LOADING -> gridlayoutManager.spanCount
                    ActorAdapter.TYPE_ACTOR -> 1
                    else -> -1
                }
            }
        }
        rvActor.apply {
            layoutManager = gridlayoutManager
            adapter = actorAdapter
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
                loadMore()
                isLoading = true
            }
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            actorList.addAll(FetchData.getActors("${urlGet}/${page}"))
            actorAdapter.notifyDataSetChanged()
            pb_actor.visibility = View.GONE
        }
    }

    private fun loadMore() {
        page++
        if (page <= totalPage!!) {
            //add null->load item
            actorList.add(Consts.NULL_ACTOR)
            actorAdapter.notifyItemInserted(actorList.size - 1)

            var more = mutableListOf<Actor>()
            CoroutineScope(Dispatchers.Main).launch {
                more = FetchData.getActors("${urlGet}/${page}")
            }

            rvActor.postDelayed(Runnable {
                //removes load item in list.
                actorList.removeAt(actorList.size - 1)
                actorAdapter.notifyItemRemoved(actorList.size)
                actorList.addAll(more)
                actorAdapter.notifyDataSetChanged()
                isLoading = false
            }, 2000)

        } else {
            Toast.makeText(context, "End of list.", Toast.LENGTH_SHORT).show()
        }
    }


}
