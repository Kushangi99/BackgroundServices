package com.example.backgroundservices

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.backgroundservices.MainActivity.Companion.INTENT_SERVICE
import android.R
import java.util.*
import androidx.annotation.NonNull
import androidx.work.ListenableWorker
import com.example.backgroundservices.MainActivity.Companion.WORKER_TAG

class WorkManagerService(var context: Context, var workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    private var mRandomNumber = 0
    private val mIsRandomGeneratorOn = true
    private val MIN = 0
    private val MAX = 100
    override fun doWork(): Result {
        Log.d(WORKER_TAG, "Work Started")
        startRandomNumberGenerator()
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.i(WORKER_TAG, "Worker has been cancelled")
    }

    private fun startRandomNumberGenerator() {
        var i = 0
        while (i < 5 && !isStopped) {
            try {
                Thread.sleep(1000)
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = Random().nextInt(MAX) + MIN
                    Log.i(
                        WORKER_TAG,
                        "Thread id: " + Thread.currentThread().id + ", Random Number: " + mRandomNumber
                    )
                    i++
                }
            } catch (e: InterruptedException) {
                Log.i(WORKER_TAG, "Thread Interrupted")
            }
        }
    }
}