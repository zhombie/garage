package kz.garage.context.system.service

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

inline val Context.activityManager: ActivityManager?
    get() = getSystemServiceCompat(ActivityManager::class.java)

inline val Context.audioManager: AudioManager?
    get() = getSystemServiceCompat(AudioManager::class.java)

inline val Context.clipboardManager: ClipboardManager?
    get() = getSystemServiceCompat(ClipboardManager::class.java)

inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemServiceCompat(ConnectivityManager::class.java)

inline val Context.inputMethodManager: InputMethodManager?
    get() = getSystemServiceCompat(InputMethodManager::class.java)

inline val Context.locationManager: LocationManager?
    get() = getSystemServiceCompat(LocationManager::class.java)

inline val Context.notificationManager: NotificationManager?
    get() = getSystemServiceCompat(NotificationManager::class.java)

inline val Context.sensorManager: SensorManager?
    get() = getSystemServiceCompat(SensorManager::class.java)

inline val Context.telephonyManager: TelephonyManager?
    get() = getSystemServiceCompat(TelephonyManager::class.java)

inline val Context.vibrator: Vibrator?
    get() = getSystemServiceCompat(Vibrator::class.java)

inline val Context.wifiManager: WifiManager?
    get() = getSystemServiceCompat(WifiManager::class.java)

inline val Context.windowManager: WindowManager?
    get() = getSystemServiceCompat(WindowManager::class.java)

inline fun <reified T> Context.getSystemServiceCompat(serviceClass: Class<T>): T? =
    ContextCompat.getSystemService(this, serviceClass)
