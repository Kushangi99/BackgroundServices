package com.example.backgroundservices

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.backgroundservices.MainActivity.Companion.INTENT_SERVICE

class WorkManagerService(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val songName = inputData.getString(INTENT_SERVICE)
        downloadSong(songName)
//        sendMessageToUi(songName)
        val outputData = workDataOf(MainActivity.MESSAGE_KEY to songName)
        return Result.success(outputData)
    }

    private fun downloadSong(songName: String?) {
        Log.d(TAG, "run: staring download")
        if (isStopped) return
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "downloadSong: $songName Downloaded...")
    }

    override fun onStopped() {
        super.onStopped()
            Log.d(TAG, "onStopped: Worker")
            Log.d(TAG, "onStopped: Thread name: " + Thread.currentThread().name)
    }

    companion object {
        private const val TAG = "MyTag"
    }
}