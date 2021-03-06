package kz.garage.activity.intent

import android.app.Activity
import android.content.Intent

inline fun <reified T: Any> Activity.createIntent(
    noinline builder: Intent.() -> Unit = {}
): Intent = Intent(this, T::class.java)
    .apply(builder)

inline fun <reified T: Any> Activity.startActivity(
    noinline builder: Intent.() -> Unit = {}
): Intent {
    val intent = createIntent<T>(builder)
    startActivity(intent)
    return intent
}
