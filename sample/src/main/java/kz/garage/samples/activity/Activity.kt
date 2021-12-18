package kz.garage.samples.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.keyboard.hideKeyboard
import kz.garage.activity.keyboard.isKeyboardVisible
import kz.garage.activity.keyboard.showKeyboard
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleNameOf

class Activity : AppCompatActivity() {

    companion object {
        private val TAG = simpleNameOf<Activity>()
    }

    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        setupButton()
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