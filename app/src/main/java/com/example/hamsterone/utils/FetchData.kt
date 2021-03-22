package com.example.hamsterone.utils

import android.util.Log
import com.example.hamsterone.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

object FetchData {

    suspend fun getPhotos(url: String): MutableList<MyPhoto> {
        var result = mutableListOf<MyPhoto>()
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()
            val elPhotos = doc.getElementsByClass("photo-container photo-thumb role-pop")
            var size = elPhotos.size
            Log.e("Thumb size", "${size}")

            for (i: Int in 0 until size) {
                var id = elPhotos[i].attr("href").substringAfterLast("/")
                var url = elPhotos[i].getElementsByClass("image-thumb")
                    .attr("data-lazy")
                var type = url.substringAfterLast(".")

                result.add(MyPhoto(id, url.replace("https", "http"), type))
//                Log.e("Video", "${i} - ${type} - ${id} - ${url}")
            }

        }
//        Collections.shuffle(result)
        return result
    }

    suspend fun getGalleries(url: String): MutableList<Gallery> {
        var result = mutableListOf<Gallery>()
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()

            val elGalleries = doc.getElementsByClass("thumb-list__item gallery-thumb")
            var size = elGalleries.size
//            Log.e("Thumb size", "${size}")

            for (i: Int in 0 until size) {

                var href =
                    elGalleries[i].getElementsByClass("gallery-thumb__link thumb-image-container role-pop")
                        .attr("href")
                var id = href.substringAfterLast("-").toLong()
                var thumb =
                    elGalleries[i].getElementsByClass("gallery-thumb__link thumb-image-container role-pop")
                        .select("img").attr("data-lazy")
                var title =
                    elGalleries[i].getElementsByClass("gallery-thumb__link thumb-image-container role-pop")
                        .select("img").attr("alt")
                var count =
                    elGalleries[i].getElementsByClass("gallery-thumb__link thumb-image-container role-pop")
                        .select("span").first().text()
                var views =
                    elGalleries[i].getElementsByClass("gallery-thumb__link thumb-image-container role-pop")
                        .select("span").last().text()

                result.add(Gallery(id, title, views, count, thumb.replace("https", "http"), href, false))
//                Log.e("Video", "${i} - ${count} - ${views}")
            }

        }
//        Collections.shuffle(result)
        return result
    }

    suspend fun getActors(url: String): MutableList<Actor> {
        var result = mutableListOf<Actor>()
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()
            val elActors = doc.getElementsByClass("pornstar-thumb-container")
            var size = elActors.size
//            Log.e("Thumb size", "${size}")

            for (i: Int in 0 until size) {
                var href = elActors[i].getElementsByClass("pornstar-thumb-container__image")
                    .attr("href")
                var thumb = elActors[i].getElementsByClass("pornstar-thumb-container__image")
                    .select("img").attr("src")
                var name = elActors[i].getElementsByClass("pornstar-thumb-container__image")
                    .select("img").attr("alt")
                var views = elActors[i].getElementsByClass("metric-container views")
                    .text()
                var videos = elActors[i].getElementsByClass("metric-container videos")
                    .text()
                var id = thumb.substringAfterLast("v").toLong()

                result.add(Actor(id, name, views, videos, thumb.replace("https", "http"), href, false))
//                Log.e("Actor", "${i} - ${name} - ${views} - ${videos} - ${thumb} - ${id}")
            }

        }
//        Collections.shuffle(result)
        return result
    }

    fun getCategories(): ArrayList<Category> {
        var result = arrayListOf<Category>()
        var size = Consts.CATEGORY_TITLES.size
        for (i: Int in 0 until size) {
            result.add(
                Category(
                    Consts.CATEGORY_TITLES[i],
                    Consts.CATEGORY_THUMBS[i].replace("https", "http")
                )
            )
        }
//        Collections.shuffle(result)
        return result
    }

    suspend fun getVideos(url: String): MutableList<Video> {
        var result = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()

            //get thumb & data-previewvideo
            val elVideos = doc.getElementsByClass("thumb-list__item video-thumb role-pop")
            var size = elVideos.size
//            Log.e("Thumb size", "${size}")

            for (i: Int in 0 until size) {

                var id = elVideos[i].attr("data-video-id").toLong()

                // get data-previewvideo
                var preview =
                    elVideos[i].getElementsByClass("video-thumb__image-container role-pop thumb-image-container")
                        .attr("data-previewvideo")

                var thumb = elVideos[i]
                    .getElementsByClass("video-thumb__image-container role-pop thumb-image-container")
                    .select("img").attr("src")
                var title = elVideos[i].getElementsByClass("video-thumb-info")
                    .select("a").text()

                var href =
                    elVideos[i].getElementsByClass("video-thumb__image-container role-pop thumb-image-container")
                        .attr("href")
                var idVideo = href.substringAfterLast("-")
                var embeb = "https://xhamster.one/embed/${idVideo}"

                var view =
                    elVideos[i].getElementsByClass("video-thumb-info__metric-container views")
                        .select("span").text()

                var like =
                    elVideos[i].getElementsByClass("video-thumb-info__metric-container rating colored-green")
                        .select("span").text()

//                Log.e("Video", "${i} - ${href} - ${idVideo}")

                result.add(
                    Video(id, idVideo, title, view, like, thumb.replace("https", "http"), preview, embeb, href, false)
                )
            }

        }
//        Collections.shuffle(result)
        return result
    }
}