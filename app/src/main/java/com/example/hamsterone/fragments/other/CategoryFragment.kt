package com.example.hamsterone.fragments.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hamsterone.R
import com.example.hamsterone.adapters.CategoryAdapter
import com.example.hamsterone.utils.FetchData
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = FetchData.getCategories()
        var gridLayoutManager = GridLayoutManager(context, 2)
        var categoryAdapter = CategoryAdapter(list)
        rv_category.layoutManager = gridLayoutManager
        rv_category.adapter = categoryAdapter
    }
}