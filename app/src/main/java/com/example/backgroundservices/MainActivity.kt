package com.example.backgroundservices

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequest

import androidx.work.WorkManager





class MainActivity : AppCompatActivity() {
    private var mLog: TextView? = null
    private var mProgressBar: ProgressBar? = null

    //    private val broadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val songName = intent?.getStringExtra(MESSAGE_KEY)
//            Log.d(TAG, "Song Downloaded...")
//            mLog!!.append("$songName downloaded\n")
//            Log.d(TAG, "OnReceive: Thread name:" + Thread.currentThread().name)
//        }
//    }
    private lateinit var workManager: WorkManager
    private lateinit var workRequest: OneTimeWorkRequest
    private lateinit var workRequestObject: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        workManager = WorkManager.getInstance(applicationContext)

        //workRequest = OneTimeWorkRequest.from(RandomNumberGeneratorWorker.class);


        //workRequest = OneTimeWorkRequest.from(RandomNumberGeneratorWorker.class);
        workRequest = OneTimeWorkRequest.Builder(
            WorkManagerService::class.java,
        ).setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.METERED).build()).build()
        if (this::workRequestObject.isInitialized) {
            workManager.getWorkInfoByIdLiveData(workRequestObject.id).observe(this,
                { workInfo ->
                    if (workInfo != null) {
                        println("####status-> ${workInfo.state}")
                        val wasSuccess = workInfo.outputData.getString(INTENT_SERVICE)
                        Log.d(TAG, "Song Downloaded...")
                        mLog!!.append("$wasSuccess downloaded\n")
                        Log.d(TAG, "OnReceive: Thread name:" + Thread.currentThread().name)
                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()
//        LocalBroadcastManager.getInstance(applicationContext)
//            .registerReceiver(broadcastReceiver, IntentFilter(INTENT_SERVICE_MESSAGE))
    }

    override fun onStop() {
        super.onStop()
//        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadcastReceiver)
    }

    fun runCode(v: View?) {
        Log.d(TAG, "Running code")
        mLog!!.append("Running code\n")
        displayProgressBar(true)
        workManager.enqueue(workRequest)
        //start the service
        //send intent to download service for all songs

        //workRequest = OneTimeWorkRequest.from(RandomNumberGeneratorWorker.class)


//        workRequest = OneTimeWorkRequest.(WorkManagerService.class)
//
//        val data = Data.Builder()
////        for (song in Playlist.songs) {
//        //Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
//            data.putString(INTENT_SERVICE, Playlist.songs[0])
//        //Set Input Data
//        workRequest.setInputData(data.build())
//        Log.d(TAG, "runCode: Enqueue")
//        workRequestObject = workRequest.build()
//        workManager.enqueue(workRequestObject)
//        }
//        val workInfo = workManager.getWorkInfoById(workRequest.build().id).get()
//        if (workInfo!=null) {
//            val wasSuccess = workInfo.outputData.getString(INTENT_SERVICE_MESSAGE)
//            Log.d(TAG, "Song Downloaded...")
//            mLog!!.append("$wasSuccess downloaded\n")
//            Log.d(TAG, "OnReceive: Thread name:" + Thread.currentThread().name)
//        }
    }

    private fun initViews() {
        mLog = findViewById(R.id.tvLog)
        mProgressBar = findViewById(R.id.progress_bar)
//        workManager = WorkManager.getInstance(this)
//        workRequest = OneTimeWorkRequestBuilder<WorkManagerService>()

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