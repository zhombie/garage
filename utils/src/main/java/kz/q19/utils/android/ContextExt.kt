package kz.q19.utils.android

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.hardware.SensorManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.view.Display
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

/**
 * Services
 */
val Context.activityManager: ActivityManager?
    get() = getSystemServiceCompat(ActivityManager::class.java)

val Context.audioManager: AudioManager?
    get() = getSystemServiceCompat(AudioManager::class.java)

val Context.clipboardManager: ClipboardManager?
    get() = getSystemServiceCompat(ClipboardManager::class.java)

val Context.connectivityManager: ConnectivityManager?
    get() = getSystemServiceCompat(ConnectivityManager::class.java)

val Context.inputMethodManager: InputMethodManager?
    get() = getSystemServiceCompat(InputMethodManager::class.java)

val Context.locationManager: LocationManager?
    get() = getSystemServiceCompat(LocationManager::class.java)

val Context.notificationManager: NotificationManager?
    get() = getSystemServiceCompat(NotificationManager::class.java)

val Context.sensorManager: SensorManager?
    get() = getSystemServiceCompat(SensorManager::class.java)

val Context.telephonyManager: TelephonyManager?
    get() = getSystemServiceCompat(TelephonyManager::class.java)

val Context.vibrator: Vibrator?
    get() = getSystemServiceCompat(Vibrator::class.java)

val Context.wifiManager: WifiManager?
    get() = getSystemServiceCompat(WifiManager::class.java)

val Context.windowManager: WindowManager?
    get() = getSystemServiceCompat(WindowManager::class.java)

fun <T> Context.getSystemServiceCompat(serviceClass: Class<T>): T? {
    return ContextCompat.getSystemService(this, serviceClass)
}


/**
 * Display
 */
fun Context.getDisplayCompat(): Display? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display
    } else {
        @Suppress("DEPRECATION")
        windowManager?.defaultDisplay
    }
}


/**
 * UI
 */
val Context.screenWidth: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        display?.getSize(size)
        return size.x
    }

val Context.screenHeight: Int
    get() {
        val display: Display? = getDisplayCompat()
        val size = Point()
        display?.getSize(size)
        return size.y
    }

fun Int.dp2Px(): Float = toFloat().dp2Px()

fun Float.dp2Px(): Float = this * Resources.getSystem().displayMetrics.density

fun Int.px2Dp(): Float = toFloat().px2Dp()

fun Float.px2Dp(): Float = this / Resources.getSystem().displayMetrics.density


/**
 * Other
 */
fun Context.hasMicrophone(): Boolean =
    packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
