package com.example.backgroundservices

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.backgroundservices.MainActivity.Companion.INTENT_SERVICE_MESSAGE

class BackgroundIntentService : IntentService("MyIntentService") {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: MyIntentService")
        Log.d(TAG, "onCreate: Thread name: " + Thread.currentThread().name)
    }

    //Receives intent ( data ) to execute tasks in background or worker thread
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: MyIntentService")
        Log.d(TAG, "onHandleIntent: Thread name: " + Thread.currentThread().name)
        val songName = intent!!.getStringExtra(MainActivity.MESSAGE_KEY)
        downloadSong(songName)
        sendMessageToUi(songName)
    }

    private fun sendMessageToUi(songName: String?) {
        val intent = Intent(INTENT_SERVICE_MESSAGE)
        intent.putExtra(MainActivity.MESSAGE_KEY, songName)
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(intent)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: MyIntentService")
        Log.d(TAG, "onDestroy: Thread name: " + Thread.currentThread().name)
    }

    companion object {
        private const val TAG = "MyTag"
    }

    init {
        setIntentRedelivery(true)
    }
}