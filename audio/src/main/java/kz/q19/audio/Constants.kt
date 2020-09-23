package kz.q19.audio

import kz.q19.utils.file.Extension

internal object Constants {

    const val RECORDS_DIR = "records"

    /**
     * Threshold in second which defines when record is considered as long or short.
     * For short and long records used a bit different visualisation algorithm.
     */
    const val LONG_RECORD_THRESHOLD_SECONDS: Long = 20L

    // -------------- Waveform visualisation constants ----------------------------------
    /**
     * Density pixel count per one second of time.
     * Used for short records (shorter than [LONG_RECORD_THRESHOLD_SECONDS])
     */
    const val SHORT_RECORD_DP_PER_SECOND: Long = 25L

    /**
     * Waveform length, measured in screens count of device.
     * Used for long records (longer than [LONG_RECORD_THRESHOLD_SECONDS])
     */
    const val WAVEFORM_WIDTH = 1.5f // one and half of screen waveform width.

    /** Time interval for Recording progress visualisation.  */
    const val VISUALIZATION_INTERVAL: Long = 1000L / SHORT_RECORD_DP_PER_SECOND // 1000 mills/25 dp per sec

    val SUPPORTED_EXT: Array<String> =
        arrayOf("mp3", "wav", "3gpp", "3gp", "amr", "aac", "m4a", "mp4", "ogg", "flac")

    const val EXTENSION_SEPARATOR = "."

    const val TRASH_MARK_EXTENSION = "del"

    const val RECORD_ENCODING_BITRATE_12000 = 12000 // Bitrate for 3gp format
    const val RECORD_ENCODING_BITRATE_24000 = 24000
    const val RECORD_ENCODING_BITRATE_48000 = 48000
    const val RECORD_ENCODING_BITRATE_96000 = 96000
    const val RECORD_ENCODING_BITRATE_128000 = 128000
    const val RECORD_ENCODING_BITRATE_192000 = 192000
    const val RECORD_ENCODING_BITRATE_256000 = 256000

    const val RECORD_AUDIO_MONO = 1
    const val RECORD_AUDIO_STEREO = 2

    const val RECORD_SAMPLE_RATE_8000 = 8000
    const val RECORD_SAMPLE_RATE_16000 = 16000
    const val RECORD_SAMPLE_RATE_22050 = 22050
    const val RECORD_SAMPLE_RATE_32000 = 32000
    const val RECORD_SAMPLE_RATE_44100 = 44100
    const val RECORD_SAMPLE_RATE_48000 = 48000

    val DEFAULT_RECORDING_FORMAT: String
        get() = Extension.M4A.value

    const val RECORD_MAX_DURATION: Long = 10 * 60 * 1000 // 10 minutes

    const val MIN_REMAIN_RECORDING_TIME: Long = 10 * 1000 // 10 Seconds

}