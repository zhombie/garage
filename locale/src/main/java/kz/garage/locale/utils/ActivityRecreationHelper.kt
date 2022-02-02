package kz.garage.locale.utils

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import java.util.*

/**
 * A helper class with methods to detect Locale changes and recreate the Activities accordingly
 */
object ActivityRecreationHelper {

    private val localesOnActivities: MutableMap<String, Locale> = HashMap()

    /**
     * Call this method on the Activity onResume. It will recreate the Activity if a Locale change is detected.
     * @param activity
     */
    fun onResume(activity: Activity) {
        val previousLocale = localesOnActivities[activity.toString()]
        val shouldRestartActivity = previousLocale != null && previousLocale != Locale.getDefault()
        localesOnActivities[activity.toString()] = Locale.getDefault()
        if (shouldRestartActivity) {
            recreate(activity, false)
        }
    }

    /**
     * Call this method on the Activity onDestroy.
     * @param activity
     */
    fun onDestroy(activity: Activity) {
        localesOnActivities.remove(activity.toString())
    }

    /**
     * Helper method to recreate the Activity. This method should be called after a Locale change.
     * @param activity the Activity that will be recreated
     * @param animate a flag indicating if the recreation will be animated or not
     */
    fun recreate(activity: Activity, animate: Boolean) {
        val restartIntent = Intent(activity, activity.javaClass)
        val extras = activity.intent.extras
        if (extras != null) {
            restartIntent.putExtras(extras)
        }
        if (animate) {
            ActivityCompat.startActivity(
                activity,
                restartIntent,
                ActivityOptionsCompat
                    .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                    .toBundle()
            )
        } else {
            activity.startActivity(restartIntent)
            activity.overridePendingTransition(0, 0)
        }
        activity.finish()
    }

}