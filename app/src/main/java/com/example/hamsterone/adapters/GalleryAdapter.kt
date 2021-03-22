package com.example.hamsterone.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.hamsterone.R
import com.example.hamsterone.activiries.DetailGalleryActivity
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.utils.Helpers
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_actor.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter(
    var list: MutableList<Gallery>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_GALLERY = 1
        const val TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_GALLERY -> {
                GalleryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_gallery, parent, false)
                )
            }
            TYPE_LOADING -> {
                LoadingHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                )
            }
            else -> throw IllegalArgumentException("Different View type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].title.equals("null_gallery")) {
            return TYPE_LOADING
        } else {
            return TYPE_GALLERY
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GalleryViewHolder) {
            holder.onBind(list[position])
        }
    }

    class GalleryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(gallery: Gallery) {
            itemView.apply {
                tv_gall_title.text = gallery.title
                tv_gall_count.text = gallery.photoCount
                tv_gall_views.text = gallery.views

                Glide.with(iv_gall_thumb)
                    .load(gallery.thumb)
//                    .load(R.drawable.ic_photo_library_black_24dp)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_gall_load.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_gall_load.visibility = View.GONE
                            return false
                        }

                    })
                    .into(iv_gall_thumb)

                setOnClickListener {
                    var intent = Intent(it.context, DetailGalleryActivity::class.java)
                    intent.putExtra("gallery", gallery)
                    it.context.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.face_out
                    )
                }

                tv_gall_add.setOnClickListener {
                    openDialogAddGallery(it.context, gallery)
                }
            }
        }

        private fun openDialogAddGallery(context: Context?, gallery: Gallery) {
            var builder = AlertDialog.Builder(context!!)
                .setIcon(R.drawable.ic_bookmark_black_24dp)
                .setTitle("Add to bookmark")
                .setMessage("Do you want add '${gallery.title}' to bookmark list ?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                        Helpers.galleryBookmark(gallery, true)
                        Snackbar.make(itemView, "Added", Snackbar.LENGTH_SHORT).show()
                    }

                }))

            var dialog = builder.create()
            dialog.show()
        }
    }

    class LoadingHolder(view: View) : RecyclerView.ViewHolder(view)

}