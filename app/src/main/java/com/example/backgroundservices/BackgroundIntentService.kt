package com.example.backgroundservices

import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.os.Looper




class BackgroundIntentService: IntentService("IntentService") {

    private lateinit var player:MediaPlayer

    override fun onHandleIntent(p0: Intent?) {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)

        // providing the boolean
        // value as true to play
        // the audio on loop
        // starting the process
        player.start()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("services", "Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("services", "Service destroyed")
//        player.stop()
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("services", "Service stopped")
        return super.stopService(name)
    }
}