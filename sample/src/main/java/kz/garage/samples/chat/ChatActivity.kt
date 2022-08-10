package kz.garage.samples.chat

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.keyboard.hideKeyboard
import kz.garage.activity.keyboard.isKeyboardVisible
import kz.garage.activity.keyboard.showKeyboard
import kz.garage.activity.view.bind
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.kotlin.simpleNameOf

class ChatActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<ChatActivity>()
    }

    private val recyclerView by bind<RecyclerView>(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView.adapter = ChatMessagesAdapter(
            contentSourceProvider = { content ->
                val file = content.publicFile?.getFile()
                if (file?.exists() == true) {
                    ContentSourceProvider.Source.LocalFile(
                        uri = Uri.fromFile(file)
                    )
                } else {
                    ContentSourceProvider.Source.RemoteFile(
                        uri = Uri.parse("localhost"),
                    )
                }
            }
        )
    }

}