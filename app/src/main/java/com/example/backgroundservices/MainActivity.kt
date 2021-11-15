package com.example.backgroundservices

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var mLog: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private lateinit var workManager: WorkManager
    private lateinit var workRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        workManager = WorkManager.getInstance(applicationContext)

            workRequest = PeriodicWorkRequest.Builder(
                WorkManagerService::class.java,
                15,
                TimeUnit.MINUTES
            )
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.METERED).build())
                .build()
    }

    fun runCode(v: View?) {
        Log.d(TAG, "Running code")
        mLog!!.append("Running code\n")
        displayProgressBar(true)
        workManager.enqueue(workRequest)
        if (this::workRequest.isInitialized) {
            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this,
                { workInfo ->
                    if (workInfo != null) {
                        println("####status-> ${workInfo.state}")
                        val wasSuccess = workInfo.outputData.getString(WORKER_TAG)
                        if (wasSuccess!=null) {
                            mLog!!.append("$wasSuccess")
                            mProgressBar!!.visibility = View.INVISIBLE
                        }
                    }
                })
        }
    }

    private fun initViews() {
        mLog = findViewById(R.id.tvLog)
        mProgressBar = findViewById(R.id.progress_bar)
    }

    fun clearOutput(v: View?) {
        workManager.cancelWorkById(workRequest.id)
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
        const val INTENT_SERVICE = "intent_service"
        const val WORKER_TAG = "worker"
    }
}