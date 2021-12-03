package kz.garage.samples.window

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.bind
import kz.garage.activity.hideKeyboard
import kz.garage.activity.isKeyboardVisible
import kz.garage.activity.showKeyboard
import kz.garage.window.addKeyboardVisibilityListener

class WindowActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, WindowActivity::class.java)
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)

        setupKeyboardListener()
        setupButton()
    }

    private fun setupKeyboardListener() {
        window.addKeyboardVisibilityListener { isVisible ->
            if (isVisible) {
                textView.text = "Visible"
            } else {
                textView.text = "Hidden"
            }
        }
    }

    private fun setupButton() {
        toggleKeyboard()

        button.setOnClickListener {
            toggleKeyboard()
        }
    }

    private fun toggleKeyboard() {
        if (isKeyboardVisible()) {
            if (hideKeyboard()) {
                button.text = "Show keyboard"
            }
        } else {
            if (showKeyboard()) {
                button.text = "Hide keyboard"
            }
        }
    }

}