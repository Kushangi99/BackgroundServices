package com.example.backgroundservices

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.s.R
import com.example.s.R.id

class MainActivity : AppCompatActivity() {
    private var mLog: TextView? = null
    private var mProgressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun runCode(v: View?) {
        Log.d(TAG, "Running code")
        mLog!!.append(
            """
                Running code  
                """.trimIndent()
        )
        displayProgressBar(true)
        //start the service
        //send intent to download service for all songs
        for (song in Playlist.songs) {
            val intent = Intent(this@MainActivity, DownloadService::class.java)
            intent.putExtra(MESSAGE_KEY, song)
            startService(intent)
        }
    }

    private fun initViews() {
        mLog = findViewById(id.tvLog)
        mProgressBar = findViewById(id.progress_bar)
    }

    fun clearOutput(v: View?) {
        val intent = Intent(this@MainActivity, DownloadService::class.java)
        stopService(intent)
        mLog!!.text = ""
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
    }
}