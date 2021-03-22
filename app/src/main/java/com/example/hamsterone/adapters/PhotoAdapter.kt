package com.example.hamsterone.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.hamsterone.R
import com.example.hamsterone.fragments.other.SlideShowFragment
import com.example.hamsterone.models.MyPhoto
import com.example.hamsterone.utils.IHorizontalClickListener
import kotlinx.android.synthetic.main.item_photo_horizontal.view.*
import kotlinx.android.synthetic.main.item_photo_recycler_view.view.*
import kotlinx.android.synthetic.main.item_photo_view_pager.view.*

class PhotoAdapter(
    var list: MutableList<MyPhoto>,
    var type: Int,
    var iHorizontalClickListener: IHorizontalClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var indexPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (type) {
            1 -> {
                return PhotoHolder1(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_photo_recycler_view, parent, false)
                )
            }
            2 -> {
                return PhotoHolder2(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_photo_view_pager, parent, false)
                )
            }
            else -> {
                return PhotoHolder3(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_photo_horizontal, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoHolder1) {
            holder.bindPhoto(list[position])
        } else if (holder is PhotoHolder2) {
            holder.bindPhoto(list[position])
        } else if (holder is PhotoHolder3) {
            holder.bindPhoto(list[position])

            if (indexPosition == position) {
                holder.itemView.indicator.visibility = View.VISIBLE
                holder.itemView.iv_photo_horizontal.setBackgroundColor(Color.parseColor("#FF9800"))
                holder.itemView.iv_photo_horizontal.setPadding(8, 8, 8, 8)
            } else {
                holder.itemView.indicator.visibility = View.INVISIBLE
                holder.itemView.iv_photo_horizontal.setBackgroundColor(Color.TRANSPARENT)
                holder.itemView.iv_photo_horizontal.setPadding(0, 0, 0, 0)
            }
        }
    }

    inner class PhotoHolder1(v: View) : RecyclerView.ViewHolder(v) {
        fun bindPhoto(myPhoto: MyPhoto) {
            itemView.apply {
                Glide.with(iv_photo_thumb)
                    .load(myPhoto.url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_photo_load.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_photo_load.visibility = View.GONE
                            return false
                        }

                    })
                    .into(iv_photo_thumb)

                setOnClickListener {
                    var fm = (it.context as AppCompatActivity).supportFragmentManager
                    var slideShowFragment = SlideShowFragment.newInstance(list, adapterPosition)
                    slideShowFragment.show(fm, "slide_show")
                }
            }
        }
    }

    inner class PhotoHolder2(v: View) : RecyclerView.ViewHolder(v) {
        fun bindPhoto(myPhoto: MyPhoto) {
            itemView.apply {
                Glide.with(iv_photo_view_pager)
                    .load(myPhoto.url)
                    .into(iv_photo_view_pager)
            }
        }
    }

    inner class PhotoHolder3(v: View) : RecyclerView.ViewHolder(v) {
        fun bindPhoto(myPhoto: MyPhoto) {
            itemView.apply {
                Glide.with(iv_photo_horizontal)
                    .load(myPhoto.url)
                    .into(iv_photo_horizontal)

                setOnClickListener {
                    handlerItemClick(adapterPosition)
                }
            }
        }
    }

    fun handlerItemClick(position: Int) {
        indexPosition = position
        iHorizontalClickListener?.onItemClick(position)
        notifyDataSetChanged()
    }

}