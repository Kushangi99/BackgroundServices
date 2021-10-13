package com.example.backgroundservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.s.R
import com.example.s.R.id

class MainActivity : AppCompatActivity() {
    private var mLog: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val songName = intent?.getStringExtra(MESSAGE_KEY)
            Log.d(TAG, "Song Downloaded...")
            mLog!!.append("$songName downloaded\n")
            Log.d(TAG, "OnReceive: Thread name:" + Thread.currentThread().name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(broadcastReceiver, IntentFilter(INTENT_SERVICE_MESSAGE))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadcastReceiver)
    }

    fun runCode(v: View?) {
        Log.d(TAG, "Running code")
        mLog!!.append("Running code\n")
        displayProgressBar(true)
        //start the service
        //send intent to download service for all songs
        for (song in Playlist.songs) {
            val intent = Intent(this@MainActivity, BackgroundIntentService::class.java)
            intent.putExtra(MESSAGE_KEY, song)
            BackgroundIntentService.enqueueWork(this, intent)
        }
    }

    private fun initViews() {
        mLog = findViewById(id.tvLog)
        mProgressBar = findViewById(id.progress_bar)
    }

    fun clearOutput(v: View?) {
        val intent = Intent(this@MainActivity, BackgroundIntentService::class.java)
        stopService(intent)
        mLog!!.text = ""
        displayProgressBar(false)
    }

    private fun displayProgressBar(display: Boolean) {
        if (display) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val TAG = "MyTag"
        const val MESSAGE_KEY = "message_key"
        const val INTENT_SERVICE_MESSAGE = "intent_service_message"
    }
}