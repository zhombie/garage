package kz.garage.samples.fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.fragment.keyboard.hideKeyboard
import kz.garage.fragment.keyboard.isKeyboardVisible
import kz.garage.fragment.keyboard.showKeyboard

class Fragment : androidx.fragment.app.Fragment(R.layout.fragment) {

    companion object {
        fun newInstance(): Fragment {
            return Fragment()
        }
    }

    private var button: MaterialButton? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.button)

        setupButton()
    }

    private fun setupButton() {
        toggleKeyboard()

        button?.setOnClickListener {
            toggleKeyboard()
        }
    }

    private fun toggleKeyboard() {
        if (isKeyboardVisible()) {
            if (hideKeyboard()) {
                button?.text = "Show keyboard"
            }
        } else {
            if (showKeyboard()) {
                button?.text = "Hide keyboard"
            }
        }
    }

}