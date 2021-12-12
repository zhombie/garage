package kz.garage.samples.window.keyboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kz.garage.R
import kz.garage.activity.keyboard.hideKeyboard
import kz.garage.activity.keyboard.isKeyboardVisible
import kz.garage.activity.keyboard.showKeyboard
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleName
import kz.garage.window.keyboard.addKeyboardVisibilityListener

@SuppressLint("SetTextI18n")
class KeyboardActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

    private val textView by bind<MaterialTextView>(R.id.textView)
    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_keyboard)

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