package com.sm.mylibrary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm.mylibrary.adapter.NotificationAdapter
import com.sm.mylibrary.databinding.ActivityNotificationBinding
import com.sm.mylibrary.model.notififation.NotificationDetails

class ActivityNotification : AppCompatActivity() {
    var activityNotificationBinding : ActivityNotificationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         activityNotificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
          setContentView(activityNotificationBinding?.root)

        val notifications = intent.getSerializableExtra("notification") as? ArrayList<NotificationDetails>


        activityNotificationBinding?.rvNotifications?.layoutManager = LinearLayoutManager(this)
        activityNotificationBinding?.rvNotifications?.adapter = NotificationAdapter(notifications!!)

        activityNotificationBinding?.imgBack?.setOnClickListener {
            finish()
        }

    }
}