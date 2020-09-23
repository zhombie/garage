package kz.q19.audio

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.CountDownLatch

class BackgroundQueue(threadName: String) : Thread() {

    companion object {
        private const val TAG = "BackgroundQueue"
    }

    @Volatile
    private var handler: Handler? = null

    private val countDownLatch = CountDownLatch(1)

    init {
        name = threadName
        start()
    }

    fun postRunnable(runnable: Runnable, delay: Long = 0) {
        try {
            countDownLatch.await()
            if (delay <= 0) {
                handler?.post(runnable)
            } else {
                handler?.postDelayed(runnable, delay)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun cancelRunnable(runnable: Runnable) {
        try {
            countDownLatch.await()
            handler?.removeCallbacks(runnable)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun cleanupQueue() {
        try {
            countDownLatch.await()
            handler?.removeCallbacksAndMessages(null)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun close() {
        handler?.looper?.quit()
    }

    override fun run() {
        Looper.prepare()
        handler = Handler()
        countDownLatch.countDown()
        Looper.loop()
    }

}