package kz.garage.samples.retrofit.download

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.garage.R
import kz.garage.activity.toast.toast
import kz.garage.activity.view.bind
import kz.garage.retrofit.download.DownloadStateListener
import kz.garage.retrofit.download.FileWriter
import java.io.File

@SuppressLint("SetTextI18n")
class RetrofitDownloadActivity : AppCompatActivity() {

    companion object {
        private val TAG = RetrofitDownloadActivity::class.java.simpleName

        private const val BASE_URL = "https://codeload.github.com"
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val button by bind<MaterialButton>(R.id.button)

    private val downloadService by lazy {
        APIManager.createService(DownloadService::class.java, BASE_URL)
    }

    private var fileWriter: FileWriter? = null

    private var state: DownloadStateListener.State? = null
        set(value) {
            field = value

            lifecycleScope.launch {
                when (value) {
                    is DownloadStateListener.State.Loading -> {
                        textView.text = "Status: $value: ${value.percentage}"

                        button.text = "Cancel download"
                    }
                    is DownloadStateListener.State.Completed -> {
                        textView.text = "Status: $value"

                        button.text = "Open file"
                    }
                    else -> {
                        textView.text = "Status: $value"

                        button.text = "Download"
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_download)

        state = null

        button.setOnClickListener {
            when (state) {
                is DownloadStateListener.State.Loading -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        fileWriter?.cancel()
                        fileWriter = null

                        state = null
                    }
                }
                is DownloadStateListener.State.Completed -> {
                    toast("Downloaded")
                }
                else -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val url = "/DrKLO/Telegram/zip/refs/heads/master"

                        val responseBody = try {
                            downloadService.download(url) { state ->
                                this@RetrofitDownloadActivity.state = state
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }

                        Log.d(TAG, "[RESULT] responseBody: $responseBody")

                        val directory =
                            getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: filesDir
                        val filename = "DOWNLOAD_${System.currentTimeMillis()}"

                        fileWriter = FileWriter(outputFile = File(directory, filename))
                        val outputFile = fileWriter?.write(responseBody)

                        Log.d(TAG, "[RESULT] outputFile: $outputFile")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy() -> $fileWriter")

        lifecycleScope.launch(Dispatchers.IO) {
            fileWriter?.cancel()
            fileWriter = null
        }
    }

}