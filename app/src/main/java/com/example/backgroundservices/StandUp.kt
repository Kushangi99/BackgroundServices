package com.example.backgroundservices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class StandUp: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Inside Receiver", Toast.LENGTH_SHORT).show()
       showNotification(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "channelId", "Stand UP Notification", NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val intent = Intent(context, StandUpActivity::class.java)
        val builder = NotificationCompat.Builder(
            context, "channelId"
        )
            .setContentTitle("Stand UP Notification")
            .setContentText("You need to Stand Up")
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentIntent(PendingIntent.getActivity(context,0, intent,0))
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }
}