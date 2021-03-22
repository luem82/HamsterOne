package com.example.hamsterone.fragments.actor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hamsterone.R
import com.example.hamsterone.adapters.ViewPagerAdapter
import com.example.hamsterone.utils.Consts
import kotlinx.android.synthetic.main.fragment_actor.*

class ActorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewPagerAdapter = ViewPagerAdapter(activity!!.supportFragmentManager)

        viewPagerAdapter.addFragment(
            ChildActorFragment.newInstance(Consts.URL_ACTOR_PORNSTAR, 350), "PornStars"
        )
        viewPagerAdapter.addFragment(
            ChildActorFragment.newInstance(Consts.URL_ACTOR_CELEBRITIES, 70), "Celebrities"
        )
        viewPagerAdapter.addFragment(
            ChildActorFragment.newInstance(Consts.URL_ACTOR_AMATEUR, 60), "Amateurs"
        )

        viewPagerActor.adapter = viewPagerAdapter
        var limit = (if (viewPagerAdapter.count > 1) viewPagerAdapter.count - 1 else 1)
        viewPagerActor.offscreenPageLimit = limit
        tabLayoutActor.setViewPager(viewPagerActor)
    }
}










