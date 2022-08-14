package kz.garage.samples.chat

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.chat.core.model.Message
import kz.garage.chat.ui.ChatMessagesAdapter
import kz.garage.chat.ui.ChatMessagesAdapterItemDecoration
import kz.garage.chat.ui.ContentSourceProvider
import kz.garage.chat.ui.components.ChatFooterView
import kz.garage.chat.ui.components.platforrm.ThemedAlertDialog
import kz.garage.chat.ui.imageloader.ChatUiImageLoader
import kz.garage.multimedia.store.model.Image
import kz.garage.samples.chat.coil.CoilImageLoader
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity(), ChatFooterView.Callback, ChatMessagesAdapter.Callback {

    companion object {
        private val TAG = ChatActivity::class.java.simpleName
    }

    private val imageLoader: ChatUiImageLoader? = null
    private var alertDialog: AlertDialog? = null
    private val adapter by lazy {
        ChatMessagesAdapter(
            imageLoader = CoilImageLoader(this, true),
            contentSourceProvider = { content ->
                val contentUri = content.uri
                if (contentUri == null) {
                    val file = content.publicFile?.getFile()
                    if (file?.exists() == true) {
                        ContentSourceProvider.Source.LocalFile(
                            uri = Uri.fromFile(file)
                        )
                    } else {
                        ContentSourceProvider.Source.RemoteFile(
                            uri = content.remoteFile?.uri ?: Uri.EMPTY,
                        )
                    }
                } else {
                    ContentSourceProvider.Source.LocalFile(
                        uri = contentUri
                    )
                }
            },
            callback = this
        )
    }

    private val requestImageFromGalleryOutgoing = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri ->
        val message = Message.Builder()
            .setRandomId()
            .setContent(Image(uri!!, "image"))
            .setOutgoingDirection()
            .setCreatedAt(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
            .build()
        adapter.addNewEntity(message)
        recyclerView.scrollToPosition(0)
    }

    private val requestImageFromGalleryIncoming = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri ->
        val message = Message.Builder()
            .setRandomId()
            .setContent(Image(uri!!, "image"))
            .setIncomingDirection()
            .setCreatedAt(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
            .build()
        adapter.addNewEntity(message)
    }

    private val recyclerView by bind<RecyclerView>(R.id.recyclerView)
    private val chatFooterView by bind<ChatFooterView>(R.id.chatFooterView)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView.adapter = adapter
        chatFooterView.callback = this
        recyclerView.addItemDecoration(ChatMessagesAdapterItemDecoration(this))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.chat_ui_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.text -> {
                val message = Message.Builder()
                    .setRandomId()
                    .setIncomingDirection()
                    .setCreatedAt(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
                    .setBody("Some message")
                    .build()
                adapter.addNewEntity(message)
            }
            R.id.photo -> {
                requestImageFromGalleryIncoming.launch("image/*")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSelectAttachment(): Boolean {
        alertDialog?.dismiss()
        alertDialog = ThemedAlertDialog.Builder(this)
            .setTitle(R.string.chat_ui_attention)
            .setItems(
                arrayOf(
                    getString(R.string.chat_ui_document),
                    getString(R.string.chat_ui_audio),
                    getString(R.string.chat_ui_video),
                    getString(R.string.chat_ui_image)
                )
            ) { dialog, which ->
                dialog.dismiss()
                when (which) {
                    0 ->
                        requestImageFromGalleryOutgoing.launch("image/*")
                    1 ->
                        requestImageFromGalleryOutgoing.launch("image/*")
                    2 ->
                        requestImageFromGalleryOutgoing.launch("image/*")
                    3 ->
                        requestImageFromGalleryOutgoing.launch("image/*")
                }
            }
            .show()
        return false
    }

    override fun onSendMessage(text: String) {
        val message = Message.Builder()
            .setRandomId()
            .setOutgoingDirection()
            .setCreatedAt(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
            .setBody(text)
            .build()
        adapter.addNewEntity(message)
        recyclerView.scrollToPosition(0)
    }

}