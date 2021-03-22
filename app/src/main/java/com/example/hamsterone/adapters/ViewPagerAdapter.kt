package com.example.hamsterone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.io.Serializable

class ViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager), Serializable {

    var listFragment: ArrayList<Fragment>
    var listTitle: ArrayList<String>

    init {
        listFragment = arrayListOf()
        listTitle = arrayListOf()
    }

    fun addFragment(fragment: Fragment, title: String) {
        listFragment.add(fragment)
        listTitle.add(title)
    }

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getCount(): Int = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? = listTitle[position]
}