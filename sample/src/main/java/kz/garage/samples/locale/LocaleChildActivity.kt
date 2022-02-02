package kz.garage.samples.locale

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.locale.base.LocaleManagerBaseActivity
import java.util.*

class LocaleChildActivity : LocaleManagerBaseActivity() {

    private val button by bind<MaterialButton>(R.id.button)
    private val button2 by bind<MaterialButton>(R.id.button2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locale)

        button.setOnClickListener {
            AlertDialog.Builder(this)
                .setItems(arrayOf("En", "Kaz", "Rus")) { dialog, which ->
                    dialog.dismiss()

                    val locale = when (which) {
                        0 -> "en"
                        1 -> "kk"
                        2 -> "ru"
                        else -> return@setItems
                    }

                    setLocale(Locale(locale))
                }
                .show()
        }

        button2.setOnClickListener {
            finish()
        }
    }

}