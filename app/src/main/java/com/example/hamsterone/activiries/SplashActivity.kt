package com.example.hamsterone.activiries

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.example.hamsterone.R
import com.example.hamsterone.utils.Consts
import com.example.hamsterone.utils.FetchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        checkInternet()
    }

    private fun checkInternet() {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager!!.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected || !networkInfo.isAvailable) {
            // when internet is inactive
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.layout_check_internet)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

            val btnTryAgain = dialog.findViewById<Button>(R.id.btn_try_again)
            btnTryAgain.setOnClickListener {
                //call recreeate()
                recreate()
                dialog.dismiss()
            }

            dialog.show()
        } else {
            // when internet is active
            // fetch data
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                startActivity(Intent(this@SplashActivity, LockActivity::class.java))
                finish()
            }

        }
    }

}
