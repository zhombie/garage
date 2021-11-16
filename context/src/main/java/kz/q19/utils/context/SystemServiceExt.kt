package kz.q19.utils.context

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.hardware.SensorManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

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

fun <T> Context.getSystemServiceCompat(serviceClass: Class<T>): T? =
    ContextCompat.getSystemService(this, serviceClass)
