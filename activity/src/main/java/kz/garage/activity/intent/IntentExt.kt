package kz.garage.activity.intent

import android.content.Context
import android.content.Intent

inline fun <reified T: Any> Context.createIntent(
    noinline builder: Intent.() -> Unit = {}
): Intent =
    Intent(this, T::class.java).apply(builder)
