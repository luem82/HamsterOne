package com.example.hamsterone.activiries

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.hamsterone.R

@Suppress("DEPRECATION")
class EmbedVideoActivity : AppCompatActivity() {

    private var embeddedUrl: String? = null
    private var webView: WebView? = null
    private var decorView: View? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embed_video)
        supportActionBar?.hide()

        embeddedUrl = intent.getStringExtra("embed")
        val frameVideo =
            "<iframe width=\"100%\" height=\"100%\" src=\"$embeddedUrl\" frameborder=\"0\" allowfullscreen></iframe>"

        supportActionBar!!.hide()
        decorView = window.decorView
        decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView?.setBackgroundColor(Color.BLACK)
        webView = findViewById(R.id.embed_video_web_view)
        webView?.setBackgroundColor(Color.TRANSPARENT)

        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        webView?.settings?.setSupportMultipleWindows(true)
//        webView?.setWebViewClient(WebViewClient())
        webView?.setWebChromeClient(WebChromeClient())
        webView?.settings?.domStorageEnabled = true

        webView?.loadDataWithBaseURL(
            "https://xhamster.one",
            frameVideo, "text/html", "UTF-8", null
        )

    }

    override fun onDestroy() {
        if (webView != null) {
            webView!!.destroy()
        }
        super.onDestroy()
    }


    override fun onPostResume() {
        decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN)
        super.onPostResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.face_in, R.anim.zoom_out)
    }
}
