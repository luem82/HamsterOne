package com.example.hamsterone.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hamsterone.R
import com.example.hamsterone.activiries.DetailActorActivity
import com.example.hamsterone.activiries.DetailGalleryActivity
import com.example.hamsterone.activiries.EmbedVideoActivity
import com.example.hamsterone.activiries.LockActivity.Companion.actorRoom
import com.example.hamsterone.activiries.LockActivity.Companion.galleryRoom
import com.example.hamsterone.activiries.LockActivity.Companion.videoRoom
import com.example.hamsterone.activiries.SearchActivity
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Gallery
import com.example.hamsterone.models.Video
import com.example.hamsterone.utils.BookmarkViewModel
import kotlinx.android.synthetic.main.item_actor.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*
import kotlinx.android.synthetic.main.item_video_bookmark.view.*

class BookmarkAdapter(
    var type: String,
    var list: MutableList<Any>,
    var tvEmpty: TextView,
    var activity: FragmentActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listSelected: MutableList<Any>? = null
    lateinit var bookmarkViewModel: BookmarkViewModel
    var isEnable = false

    init {
        when (type) {
            "video" -> {
                list as MutableList<Video>
                listSelected = mutableListOf<Video>().toMutableList()
            }
            "actor" -> {
                list as MutableList<Actor>
                listSelected = mutableListOf<Actor>().toMutableList()
            }
            "gallery" -> {
                list as MutableList<Gallery>
                listSelected = mutableListOf<Gallery>().toMutableList()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        bookmarkViewModel = ViewModelProviders.of(activity).get(BookmarkViewModel::class.java)

        return when (type) {
            "video" -> {
                return VideoHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_video_bookmark, parent, false)
                )
            }
            "actor" -> {
                return ActorHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_actor, parent, false)
                )
            }
            else -> {
                return GalleryHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_gallery, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is VideoHolder) {
            holder.bindVideo(list[position] as Video)
            if (!listSelected!!.contains(list[position] as Video)) {
                holder.itemView.iv_book_video_check.visibility = View.GONE
                holder.itemView.iv_book_video_thumb.visibility = View.VISIBLE
            } else {
                holder.itemView.iv_book_video_check.visibility = View.VISIBLE
                holder.itemView.iv_book_video_thumb.visibility = View.INVISIBLE
            }
        } else if (holder is ActorHolder) {
            holder.bindActor(list[position] as Actor)
            if (!listSelected!!.contains(list[position] as Actor)) {
                holder.itemView.iv_actor_check.visibility = View.GONE
                holder.itemView.iv_actor_thumb.visibility = View.VISIBLE
            } else {
                holder.itemView.iv_actor_check.visibility = View.VISIBLE
                holder.itemView.iv_actor_thumb.visibility = View.INVISIBLE
            }
        } else if (holder is GalleryHolder) {
            holder.bindGallery(list[position] as Gallery)
            if (!listSelected!!.contains(list[position] as Gallery)) {
                holder.itemView.iv_gallery_check.visibility = View.GONE
                holder.itemView.iv_gall_thumb.visibility = View.VISIBLE
            } else {
                holder.itemView.iv_gallery_check.visibility = View.VISIBLE
                holder.itemView.iv_gall_thumb.visibility = View.INVISIBLE
            }
        }

        holder.itemView.setOnLongClickListener {
            if (!isEnable) {
                var callback = object : ActionMode.Callback {
                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        if (item?.itemId == R.id.action_delete_bookmark) {
                            showDialogDelete(it.context, mode, holder)
                        }
                        return true
                    }

                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        (it.context as AppCompatActivity).supportActionBar?.hide()
                        var menuInflater = mode?.menuInflater
                        menuInflater?.inflate(R.menu.menu_bookmark_action_mode, menu)
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        isEnable = true
                        clickItemHolder(holder, list[position])
                        bookmarkViewModel.getCount().observe(
                            activity as LifecycleOwner, Observer<Int> {
                                mode?.title = "${it} selected"
                            }
                        )
                        return true
                    }

                    override fun onDestroyActionMode(mode: ActionMode?) {
                        (it.context as AppCompatActivity).supportActionBar?.show()
                        isEnable = false
                        listSelected?.clear()
                        notifyDataSetChanged()
                    }

                }
                (it.context as AppCompatActivity).startActionMode(callback)
            } else {
                clickItemHolder(holder, list[position])
            }
            true
        }

        holder.itemView.setOnClickListener {
            if (isEnable) {
                clickItemHolder(holder, list[position])
            } else {
                if (holder is VideoHolder) {
                    var intent = Intent(it.context, EmbedVideoActivity::class.java)
                    var video = list[position] as Video
                    intent.putExtra("embed", video.embed)
                    it.context?.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.zoom_in, R.anim.face_out
                    )

                } else if (holder is ActorHolder) {
                    var intent = Intent(it.context, DetailActorActivity::class.java)
                    var actor = list[position] as Actor
                    intent.putExtra("actor", actor)
                    it.context.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.face_out
                    )
                } else if (holder is GalleryHolder) {
                    var intent = Intent(it.context, DetailGalleryActivity::class.java)
                    var gallery = list[position] as Gallery
                    intent.putExtra("gallery", gallery)
                    it.context.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.face_out
                    )
                }
            }
        }


    }

    private fun showDialogDelete(
        context: Context?,
        mode: ActionMode?,
        holder: RecyclerView.ViewHolder
    ) {

        var build = AlertDialog.Builder(context!!)
            .setIcon(R.drawable.ic_delete_black_24dp)
            .setTitle("Delete")
            .setMessage("Are you sure you want delete all item selected ?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                    if (holder is VideoHolder) {
                        for (video: Video in listSelected as MutableList<Video>) {
                            (list as MutableList<Video>).remove(video)
                            videoRoom!!.getVideoDAO().deleteVideo(video)
                        }
                    } else if (holder is ActorHolder) {
                        for (actor: Actor in listSelected as MutableList<Actor>) {
                            (list as MutableList<Actor>).remove(actor)
                            actorRoom!!.getActorDAO().deleteActor(actor)
                        }
                    } else if (holder is GalleryHolder) {
                        for (gallery: Gallery in listSelected as MutableList<Gallery>) {
                            (list as MutableList<Gallery>).remove(gallery)
                            galleryRoom!!.getGalleryDAO().deleteGallery(gallery)
                        }
                    }

                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show()
                    isEnable = false
                    listSelected?.clear()
                    mode?.finish()
                    notifyDataSetChanged()
                    if (list.isNullOrEmpty()) {
                        tvEmpty.visibility = View.VISIBLE
                    }
                }
            })

        var dialog = build.create()
        dialog.show()

    }

    private fun clickItemHolder(holder: RecyclerView.ViewHolder, any: Any) {
        if (holder is VideoHolder) {
            var video = any as Video
            if (holder.itemView.iv_book_video_check.visibility == View.GONE) {
                holder.itemView.iv_book_video_check.visibility = View.VISIBLE
                holder.itemView.iv_book_video_thumb.visibility = View.INVISIBLE
                listSelected!!.add(video)
            } else {
                holder.itemView.iv_book_video_check.visibility = View.GONE
                holder.itemView.iv_book_video_thumb.visibility = View.VISIBLE
                listSelected!!.remove(video)
            }
            bookmarkViewModel.setCount(listSelected!!.size)
        } else if (holder is ActorHolder) {
            var actor = any as Actor
            if (holder.itemView.iv_actor_check.visibility == View.GONE) {
                holder.itemView.iv_actor_check.visibility = View.VISIBLE
                holder.itemView.iv_actor_thumb.visibility = View.INVISIBLE
                listSelected!!.add(actor)
            } else {
                holder.itemView.iv_actor_check.visibility = View.GONE
                holder.itemView.iv_actor_thumb.visibility = View.VISIBLE
                listSelected!!.remove(actor)
            }
            bookmarkViewModel.setCount(listSelected!!.size)
        } else if (holder is GalleryHolder) {
            var gallery = any as Gallery
            if (holder.itemView.iv_gallery_check.visibility == View.GONE) {
                holder.itemView.iv_gallery_check.visibility = View.VISIBLE
                holder.itemView.iv_gall_thumb.visibility = View.INVISIBLE
                listSelected!!.add(gallery)
            } else {
                holder.itemView.iv_gallery_check.visibility = View.GONE
                holder.itemView.iv_gall_thumb.visibility = View.VISIBLE
                listSelected!!.remove(gallery)
            }
            bookmarkViewModel.setCount(listSelected!!.size)
        }
    }

    class VideoHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindVideo(video: Video) {
            itemView.apply {
                tv_book_video_title.text = video.title
                tv_book_video_like.text = video.like
                tv_book_video_views.text = video.view

                Glide.with(iv_book_video_thumb)
                    .load(video.thumb)
//                    .load(R.drawable.ic_video_library_black_24dp)
                    .into(iv_book_video_thumb)
            }
        }
    }

    class ActorHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindActor(actor: Actor) {
            itemView.apply {
                tv_actor_name.text = actor.name
                tv_actor_views.text = actor.views
                tv_actor_count.text = actor.videoCount

                Glide.with(iv_actor_thumb)
                    .load(actor.thumb)
//                    .load(R.drawable.ic_person_black_24dp)
                    .into(iv_actor_thumb)

                pb_actor_load.visibility = View.GONE
                tv_actor_add.visibility = View.GONE
            }
        }
    }

    class GalleryHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindGallery(gallery: Gallery) {
            itemView.apply {
                tv_gall_title.text = gallery.title
                tv_gall_count.text = gallery.photoCount
                tv_gall_views.text = gallery.views

                Glide.with(iv_gall_thumb)
                    .load(gallery.thumb)
//                    .load(R.drawable.ic_photo_library_black_24dp)
                    .into(iv_gall_thumb)

                pb_gall_load.visibility = View.GONE
                tv_gall_add.visibility = View.GONE
            }
        }
    }

}





