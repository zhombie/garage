package kz.garage.samples.download

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.*
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.retrofit.download.DownloadStateListener
import kz.garage.retrofit.download.write
import java.io.File

class RetrofitDownloadActivity : AppCompatActivity() {

    companion object {
        private val TAG = RetrofitDownloadActivity::class.java.simpleName
    }

    private val textView by bind<MaterialTextView>(R.id.textView)

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_download)

        val baseUrl = "https://codeload.github.com"

        val downloadService = APIManager.createService(DownloadService::class.java, baseUrl)

        val url = "/DrKLO/Telegram/zip/refs/heads/master"

        job = lifecycleScope.launch(Dispatchers.IO) {
            val responseBody = try {
                downloadService.download(url) { state ->
                    if (state is DownloadStateListener.State.Loading) {
                        Log.d(TAG, "[NETWORK] state: ${state.percentage}, $state")

                        lifecycleScope.launch(Dispatchers.Main) {
                            textView.text = "$state: ${state.percentage}"
                        }
                    } else {
                        Log.d(TAG, "[NETWORK] state: $state")

                        lifecycleScope.launch(Dispatchers.Main) {
                            textView.text = "$state"
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            Log.d(TAG, "[RESULT] responseBody: $responseBody")

            val directory = getExternalFilesDir(null)
            val filename = "downloaded_" + System.currentTimeMillis().toString()

            val file = responseBody?.write(File(directory, filename))

            Log.d(TAG, "[RESULT] file: $file")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")

        lifecycleScope.launch {
            job?.cancelAndJoin()
            job = null
        }
    }

}