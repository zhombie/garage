package kz.q19.audio

interface Preferences {
    fun getActiveRecord(): Long
    fun setActiveRecord(id: Long)
}