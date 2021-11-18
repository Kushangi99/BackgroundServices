package com.example.backgroundservices

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.withStyledAttributes
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var alarmManager:AlarmManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
    }

    fun startAlarm(v: View?) {
        Toast.makeText(this, "Alarm Started", Toast.LENGTH_SHORT).show()
        val intent = Intent(applicationContext, StandUp::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,0)
        alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000*30, pendingIntent)
    }

    fun stopAlarm(v: View?) {
        Toast.makeText(this, "Alarm Stopped", Toast.LENGTH_SHORT).show()
        val intent = Intent(applicationContext, StandUp::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager?.cancel(pendingIntent)
    }
}