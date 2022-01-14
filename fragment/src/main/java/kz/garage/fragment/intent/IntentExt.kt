package kz.garage.fragment.intent

import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T: Any> Fragment.createIntent(
    noinline builder: Intent.() -> Unit = {}
): Intent = Intent(requireContext(), T::class.java)
    .apply(builder)

inline fun <reified T: Any> Fragment.startActivity(
    noinline builder: Intent.() -> Unit = {}
): Intent {
    val intent = createIntent<T>(builder)
    startActivity(intent)
    return intent
}
