package com.example.hamsterone.fragments.other


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2

import com.example.hamsterone.R
import com.example.hamsterone.adapters.PhotoAdapter
import com.example.hamsterone.models.MyPhoto
import com.example.hamsterone.utils.Helpers
import com.example.hamsterone.utils.IHorizontalClickListener
import kotlinx.android.synthetic.main.fragment_slide_show.*
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SlideShowFragment : DialogFragment(), IHorizontalClickListener {

    private var list: MutableList<MyPhoto>? = null
    private var selectPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getSerializable(ARG_PARAM1) as MutableList<MyPhoto>
            selectPosition = it.getInt(ARG_PARAM2)
        }
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.EmbedVideoAnimation
    }

    companion object {
        @JvmStatic
        fun newInstance(list: MutableList<MyPhoto>, selectPosition: Int) =
            SlideShowFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, list as Serializable)
                    putInt(ARG_PARAM2, selectPosition)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPagerSlide()
        initHorizontalRecyclerView()

        iv_close.setOnClickListener { dismiss() }
        iv_download.setOnClickListener {
            requestStoragePermissionWithAction {
                Helpers.downloadPhoto(list!![viewPagerSlide.currentItem], context!!)
            }
        }
        tv_current.text = "${selectPosition!! + 1}/${list!!.size}"
    }

    var photoAdapterHorizontal: PhotoAdapter? = null
    private fun initHorizontalRecyclerView() {
        photoAdapterHorizontal = PhotoAdapter(list!!, 3, this)
        var linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvHorizontal.layoutManager = linearLayoutManager
        rvHorizontal.adapter = photoAdapterHorizontal
        rvHorizontal.scrollToPosition(selectPosition!!)
        photoAdapterHorizontal?.handlerItemClick(selectPosition!!)
    }

    override fun onItemClick(position: Int) {
        viewPagerSlide.currentItem = position
        tv_current.text = "${position + 1}/${list!!.size}"
    }

    private fun initViewPagerSlide() {
        var photoAdapter = PhotoAdapter(list!!, 2, null)
        viewPagerSlide.adapter = photoAdapter
        viewPagerSlide.currentItem = selectPosition!!

        viewPagerSlide.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tv_current.text = "${position + 1}/${list!!.size}"
                photoAdapterHorizontal?.handlerItemClick(position)
                rvHorizontal.scrollToPosition(position)

            }
        })

        viewPagerSlide.setPageTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                //rotates the pages around their Z axis by 30 degrees
                //                page.rotation = position * -30
                //These lines perform a scaling effect from and to 50%:
                val normalizedposition = Math.abs(Math.abs(position) - 1)
                page.scaleX = normalizedposition / 2 + 0.5f
                page.scaleY = normalizedposition / 2 + 0.5f
            }

        })
    }

    private var onPermissionGrantedAction: (() -> Unit)? = null
    private val PERMISSION_CODE = 99
    fun requestStoragePermissionWithAction(permissionNeededAction: () -> Unit) {
        onPermissionGrantedAction = permissionNeededAction
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
        } else {
            onPermissionGrantedAction?.invoke()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE &&
            grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGrantedAction?.invoke()
            onPermissionGrantedAction = null
        }
    }
}
