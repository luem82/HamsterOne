package com.example.hamsterone.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.hamsterone.R
import com.example.hamsterone.activiries.DetailActorActivity
import com.example.hamsterone.activiries.SearchActivity
import com.example.hamsterone.models.Actor
import com.example.hamsterone.models.Category
import com.example.hamsterone.utils.Helpers
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_actor.view.*
import kotlinx.android.synthetic.main.item_category.view.*

class ActorAdapter(
    var list: MutableList<Actor>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ACTOR = 1
        const val TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ACTOR -> {
                ActorViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_actor, parent, false)
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
        if (list[position].name.equals("null_actor")) {
            return TYPE_LOADING
        } else {
            return TYPE_ACTOR
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActorViewHolder) {
            holder.onBind(list[position])
        }
    }

    class ActorViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(actor: Actor) {
            itemView.apply {
                tv_actor_name.text = actor.name
                tv_actor_views.text = actor.views
                tv_actor_count.text = actor.videoCount

                Glide.with(iv_actor_thumb)
                    .load(actor.thumb)
//                    .load(R.drawable.ic_person_black_24dp)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_actor_load.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_actor_load.visibility = View.GONE
                            return false
                        }

                    })
                    .into(iv_actor_thumb)

                setOnClickListener {
                    var intent = Intent(it.context, DetailActorActivity::class.java)
                    intent.putExtra("actor", actor)
                    it.context.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.face_out
                    )
                }

                tv_actor_add.setOnClickListener {
                    openDialogAddActor(it.context, actor)
                }
            }
        }

        private fun openDialogAddActor(context: Context?, actor: Actor) {
            var builder = AlertDialog.Builder(context!!)
                .setIcon(R.drawable.ic_bookmark_black_24dp)
                .setTitle("Add to bookmark")
                .setMessage("Do you want add '${actor.name}' to bookmark list ?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                        Helpers.actorBookmark(actor, true)
                        Snackbar.make(itemView, "Added", Snackbar.LENGTH_SHORT).show()
                    }

                }))

            var dialog = builder.create()
            dialog.show()
        }
    }

    class LoadingHolder(view: View) : RecyclerView.ViewHolder(view)

}