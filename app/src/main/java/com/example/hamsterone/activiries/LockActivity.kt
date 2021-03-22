package com.example.hamsterone.activiries

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.room.Room
import com.example.hamsterone.R
import com.example.hamsterone.models.ActorRoom
import com.example.hamsterone.models.GalleryRoom
import com.example.hamsterone.models.VideoRoom
import com.example.hamsterone.utils.Helpers
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : AppCompatActivity() {

    companion object {
        var videoRoom: VideoRoom? = null
        var galleryRoom: GalleryRoom? = null
        var actorRoom: ActorRoom? = null
        var sharedPreferences: SharedPreferences? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        initDatabase()
        checkPasscode()
    }

    private fun checkPasscode() {

        sharedPreferences = getSharedPreferences("LOCK_CHILD", MODE_PRIVATE)

        var isLock = sharedPreferences!!.getBoolean("lock", false)
        var passCode = sharedPreferences!!.getString("pass", "")
        if (isLock) {
//            Helpers.keyboardUtil(this, edtPasscode, true)

            //show edt passcode
            tvLetGo.setOnClickListener {
                if (!TextUtils.isEmpty(edtPasscode.text.toString())) {
                    if (passCode.equals(edtPasscode.text.toString())) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.face_out)
                        finish()
                    } else {
                        Toast.makeText(this, "Passcode is wrong...!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Enter your passcode.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.face_out)
            finish()
        }

    }

    private fun initDatabase() {
        videoRoom = Room.databaseBuilder(
            this, VideoRoom::class.java, "room_video"
        ).allowMainThreadQueries().build()

        galleryRoom = Room.databaseBuilder(
            this, GalleryRoom::class.java, "room_gallery"
        ).allowMainThreadQueries().build()

        actorRoom = Room.databaseBuilder(
            this, ActorRoom::class.java, "room_actor"
        ).allowMainThreadQueries().build()
    }
}
