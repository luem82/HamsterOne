package com.example.hamsterone.fragments.other

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hamsterone.R
import com.example.hamsterone.activiries.LockActivity.Companion.sharedPreferences
import com.example.hamsterone.utils.Helpers
import kotlinx.android.synthetic.main.fragment_lock.*

class LockFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isLock = sharedPreferences!!.getBoolean("lock", false)
        if (sharedPreferences != null && isLock) {
            edtPasscode.visibility = View.GONE
            text_layout.visibility = View.GONE
            btnSave.visibility = View.GONE
            btnTurnOff.visibility = View.VISIBLE
            tvNoti.setText(R.string.noti_lock_turn_off)
        } else {
            Helpers.keyboardUtil(context!!, edtPasscode, true)
            btnSave.visibility = View.VISIBLE
            edtPasscode.visibility = View.VISIBLE
            text_layout.visibility = View.VISIBLE
            btnTurnOff.visibility = View.GONE
            tvNoti.setText(R.string.noti_lock_turn_on)
        }

        btnTurnOff.setOnClickListener {
            if (sharedPreferences != null && isLock) {
                sharedPreferences!!.edit().clear().commit()
                Toast.makeText(context, "Lock child disable", Toast.LENGTH_SHORT).show()
                tvNoti.setText(R.string.noti_lock_turned_off)
                tvNoti.setTextColor(Color.RED)
                btnTurnOff.visibility = View.GONE
            }
        }

        btnSave.setOnClickListener {

            if (!TextUtils.isEmpty(edtPasscode.text.toString())) {
                sharedPreferences!!.edit().putBoolean("lock", true).commit()
                var pass = edtPasscode.text.toString()
                sharedPreferences!!.edit().putString("pass", pass).commit()
                Toast.makeText(context, "Lock child enable", Toast.LENGTH_SHORT).show()
                tvNoti.setText(R.string.noti_lock_turned_on)
                tvNoti.setTextColor(Color.RED)
                edtPasscode.visibility = View.GONE
                btnSave.visibility = View.GONE
                // hide keyboard
                Helpers.keyboardUtil(context!!, it, false)
            } else {
                Toast.makeText(context, "Enter passcode", Toast.LENGTH_SHORT).show()
            }
        }
    }
}