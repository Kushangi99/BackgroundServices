package com.example.backgroundservices

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class StandUp: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Inside Receiver", Toast.LENGTH_SHORT).show()
        val notificationManager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context)
        notification.setContentTitle("Stand UP Notification")
        notification.setContentText("You need to Stand Up")

        val intent = Intent(context, StandUpActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
        notification.setContentIntent(pendingIntent)
        notification.setAutoCancel(true)
        notificationManager.notify(1, notification.build())
    }
}