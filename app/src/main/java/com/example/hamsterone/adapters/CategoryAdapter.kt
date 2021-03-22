package com.example.hamsterone.adapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.hamsterone.R
import com.example.hamsterone.activiries.SearchActivity
import com.example.hamsterone.models.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    var list: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class CategoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(category: Category) {
            itemView.apply {
                tv_cate_title.text = category.title

                Glide.with(iv_cate_thumb)
                    .load(category.thumb)
//                    .load(R.drawable.ic_view_module_black_24dp)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_cate_load.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pb_cate_load.visibility = View.GONE
                            return false
                        }

                    })
                    .into(iv_cate_thumb)

                setOnClickListener {
                    var title = category.title
                    var key = category.title.replace(" ", "+")
                    var hrefVideo = "https://xhamster.one/search/${key}"
                    var hrefPhoto = "https://xhamster.one/photos/search/${key}"
                    var intent = Intent(it.context, SearchActivity::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("hrefphoto", hrefPhoto)
                    intent.putExtra("hrefvideo", hrefVideo)
                    it.context.startActivity(intent)
                    (it.context as AppCompatActivity).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.face_out
                    )
                }
            }
        }
    }
}