package com.example.backgroundservices

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.backgroundservices.MainActivity.Companion.WORKER_TAG
import java.util.*

class WorkManagerService(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var mRandomNumber = 0
    private val mIsRandomGeneratorOn = true
    private val MIN = 0
    private val MAX = 100
    override fun doWork(): Result {
        Log.d(WORKER_TAG, "Work Started")
        startRandomNumberGenerator()
        val output = Data.Builder().putString(WORKER_TAG,"Random numbers generated").build()
        return Result.success(output)
    }

    override fun onStopped() {
        super.onStopped()
        Log.i(WORKER_TAG, "Worker has been cancelled")
    }

    private fun startRandomNumberGenerator():Int {
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
        return mRandomNumber
    }
}