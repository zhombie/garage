@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package kz.q19.audio.model

class Record private constructor(
    val id: Long,
    val name: String,
    var duration: Long = 0,
    val created: Long = 0,
    val added: Long = 0,
    val removed: Long = 0,
    val path: String,
    val format: String,
    val size: Long = 0,
    val sampleRate: Int = 0,
    val channelCount: Int = 0,
    val bitrate: Int = 0,
    val isWaveformProcessed: Boolean = false,
    val amps: IntArray,
    val data: ByteArray
) {

    companion object {
        const val NO_ID = -1L

        fun int2byte(amps: IntArray): ByteArray {
            val bytes = ByteArray(amps.size)
            for (i in amps.indices) {
                when {
                    amps[i] >= 255 -> bytes[i] = 127
                    amps[i] < 0 -> bytes[i] = 0
                    else -> bytes[i] = (amps[i] - 128).toByte()
                }
            }
            return bytes
        }

        fun byte2int(amps: ByteArray): IntArray {
            val ints = IntArray(amps.size)
            for (i in amps.indices) {
                ints[i] = amps[i] + 128
            }
            return ints
        }
    }

    constructor(
        id: Long,
        name: String,
        duration: Long,
        created: Long,
        added: Long,
        removed: Long,
        path: String,
        format: String,
        size: Long,
        sampleRate: Int,
        channelCount: Int,
        bitrate: Int,
        waveformProcessed: Boolean,
        amps: IntArray
    ) : this(
        id = id,
        name = name,
        duration = duration,
        created = created,
        added = added,
        removed = removed,
        path = path,
        format = format,
        size = size,
        sampleRate = sampleRate,
        channelCount = channelCount,
        bitrate = bitrate,
        isWaveformProcessed = waveformProcessed,
        amps = amps,
        data = int2byte(amps)
    )

    constructor(
        id: Long,
        name: String,
        duration: Long,
        created: Long,
        added: Long,
        removed: Long,
        path: String,
        format: String,
        size: Long,
        sampleRate: Int,
        channelCount: Int,
        bitrate: Int,
        waveformProcessed: Boolean,
        amps: ByteArray
    ) : this(
        id = id,
        name = name,
        duration = duration,
        created = created,
        added = added,
        removed = removed,
        path = path,
        format = format,
        size = size,
        sampleRate = sampleRate,
        channelCount = channelCount,
        bitrate = bitrate,
        isWaveformProcessed = waveformProcessed,
        amps = byte2int(amps),
        data = amps
    )

    override fun toString(): String {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", created=" + created +
                ", added=" + added +
                ", removed=" + removed +
                ", path='" + path + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", sampleRate=" + sampleRate +
                ", channelCount=" + channelCount +
                ", bitrate=" + bitrate +
                ", waveformProcessed=" + isWaveformProcessed +
                ", amps=" + amps.contentToString() +
                ", data=" + data.contentToString() +
                '}'
    }

}