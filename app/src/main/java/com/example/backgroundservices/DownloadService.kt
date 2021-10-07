package com.example.backgroundservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.backgroundservices.MainActivity.Companion.INTENT_SERVICE_MESSAGE
import com.example.backgroundservices.MainActivity.Companion.MESSAGE_KEY

class DownloadService  //this is started service
    : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "On Create Invoked")
    }

    //We will receive intents for all the songs in onStartCommand
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songName = intent?.getStringExtra(MainActivity.MESSAGE_KEY)
        downloadSong(songName)
        sendMessageToUI(songName)
        stopSelf()

        // val stopSelfResult = stopSelfResult(startId)
        // Log.d(TAG, "StopSelfResult ${stopSelfResult}")
        // If the service gets crashed during execution, in what way it will get started that is mentioned by the flags
        return START_REDELIVER_INTENT
    }

    private fun sendMessageToUI(songName: String?) {
        val intent = Intent(INTENT_SERVICE_MESSAGE)
        intent.putExtra(MESSAGE_KEY, songName)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun downloadSong(songName: String?) {
        Log.d(TAG, "run: staring download")
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "downloadSong: $songName Downloaded...")
    }

    companion object {
        private const val TAG = "MyTag"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On Destroy Invoked")
    }
}