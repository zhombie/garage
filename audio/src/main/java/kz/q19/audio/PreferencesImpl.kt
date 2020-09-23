package kz.q19.audio

import android.content.Context
import android.content.SharedPreferences

class PreferencesImpl private constructor(
    context: Context
) : Preferences {

    companion object {
        private const val PREFERENCES_NAME = "kz.q19.PreferencesImpl"

        private const val KEY_ACTIVE_RECORD = "active_record"

        @Volatile
        private var instance: PreferencesImpl? = null

        fun getInstance(context: Context): PreferencesImpl {
            if (instance == null) {
                synchronized(PreferencesImpl::class.java) {
                    if (instance == null) {
                        instance = PreferencesImpl(context)
                    }
                }
            }
            return instance!!
        }
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }


    override fun getActiveRecord(): Long {
        return sharedPreferences.getLong(KEY_ACTIVE_RECORD, -1)
    }

    override fun setActiveRecord(id: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_ACTIVE_RECORD, id)
        editor.apply()
    }
    
}