package kz.garage.file.size

import kz.garage.file.size.unit.FileSizeUnit
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sign

internal const val MAX_BYTES = Long.MAX_VALUE / 2

// Inspired by: kotlin.time.Duration
@JvmInline
value class FileSize constructor(
    private val rawValue: Long
) : Comparable<FileSize> {

    companion object {
        val ZERO: FileSize = FileSize(0L)

        private val LEAST_FILE_SIZE_UNIT: FileSizeUnit = FileSizeUnit.BYTES

        val INFINITE: FileSize = fileSizeOfBytes(MAX_BYTES)
        internal val NEG_INFINITE: FileSize = fileSizeOfBytes(-MAX_BYTES)

        inline val Int.bytes: FileSize
            get() = toFileSize(FileSizeUnit.BYTES)

        inline val Long.bytes: FileSize
            get() = toFileSize(FileSizeUnit.BYTES)

        inline val Double.bytes: FileSize
            get() = toFileSize(FileSizeUnit.BYTES)

        inline val Int.kilobytes: FileSize
            get() = toFileSize(FileSizeUnit.KILOBYTES)

        inline val Long.kilobytes: FileSize
            get() = toFileSize(FileSizeUnit.KILOBYTES)

        inline val Double.kilobytes: FileSize
            get() = toFileSize(FileSizeUnit.KILOBYTES)

        inline val Int.megabytes: FileSize
            get() = toFileSize(FileSizeUnit.MEGABYTES)

        inline val Long.megabytes: FileSize
            get() = toFileSize(FileSizeUnit.MEGABYTES)

        inline val Double.megabytes: FileSize
            get() = toFileSize(FileSizeUnit.MEGABYTES)

        inline val Int.gigabytes: FileSize
            get() = toFileSize(FileSizeUnit.GIGABYTES)

        inline val Long.gigabytes: FileSize
            get() = toFileSize(FileSizeUnit.GIGABYTES)

        inline val Double.gigabytes: FileSize
            get() = toFileSize(FileSizeUnit.GIGABYTES)

        fun Int.toFileSize(unit: FileSizeUnit): FileSize =
            toLong().toFileSize(unit)

        fun Long.toFileSize(unit: FileSizeUnit): FileSize =
            FileSize(convertDurationUnit(this, unit, LEAST_FILE_SIZE_UNIT))

        fun Double.toFileSize(unit: FileSizeUnit): FileSize =
            FileSize(convertDurationUnit(this, unit, LEAST_FILE_SIZE_UNIT).roundToLong())
    }

    fun isNegative(): Boolean = rawValue < 0

    fun isPositive(): Boolean = rawValue > 0

    fun isInfinite(): Boolean =
        rawValue == INFINITE.rawValue || rawValue == NEG_INFINITE.rawValue

    fun isFinite(): Boolean = !isInfinite()

    val absoluteValue: FileSize
        get() = if (isNegative()) -this else this

    operator fun compareTo(other: Int): Int =
        compareTo(FileSize(other.toLong()))

    operator fun compareTo(other: Long): Int =
        compareTo(FileSize(other))

    override fun compareTo(other: FileSize): Int =
        rawValue.compareTo(other.rawValue)

    // splitting to components

    inline fun <T> toComponents(
        action: (gigabytes: Long, megabytes: Int, kilobytes: Int, bytes: Int) -> T
    ): T = action.invoke(inGigabytes, megabytesComponent, kilobytesComponent, bytesComponent)

    inline fun <T> toComponents(action: (megabytes: Long, kilobytes: Int, bytes: Int) -> T): T =
        action.invoke(inMegabytes, kilobytesComponent, bytesComponent)

    inline fun <T> toComponents(action: (kilobytes: Long, bytes: Int) -> T): T =
        action.invoke(inKilobytes, bytesComponent)

    @PublishedApi
    internal val gigabytesComponent: Int
        get() = if (isInfinite()) 0 else (inGigabytes % 1024).toInt()

    @PublishedApi
    internal val megabytesComponent: Int
        get() = if (isInfinite()) 0 else (inMegabytes % 1024).toInt()

    @PublishedApi
    internal val kilobytesComponent: Int
        get() = if (isInfinite()) 0 else (inKilobytes % 1024).toInt()

    @PublishedApi
    internal val bytesComponent: Int
        get() = if (isInfinite()) 0 else (rawValue % 1024).toInt()

    // arithmetic operators

    operator fun unaryMinus(): FileSize = fileSizeOfBytes(-rawValue)

    operator fun times(scale: Int): FileSize {
        if (isInfinite()) {
            return when {
                scale == 0 -> throw IllegalArgumentException("Multiplying infinite file size by zero yields an undefined result.")
                scale > 0 -> this
                else -> -this
            }
        }
        if (scale == 0) return ZERO

        val value = rawValue
        val result = value * scale
        return if (result / scale == value) {
            fileSizeOfBytes(result.coerceIn(-MAX_BYTES..MAX_BYTES))
        } else {
            if (value.sign * scale.sign > 0) INFINITE else NEG_INFINITE
        }
    }

    operator fun times(scale: Double): FileSize {
        val intScale = scale.roundToInt()
        if (intScale.toDouble() == scale) {
            return times(intScale)
        }

        val result = toDouble(LEAST_FILE_SIZE_UNIT) * scale
        return result.toFileSize(LEAST_FILE_SIZE_UNIT)
    }

    // conversion to units

    fun toInt(unit: FileSizeUnit): Int =
        toLong(unit).coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()

    fun toDouble(unit: FileSizeUnit): Double =
        when (rawValue) {
            INFINITE.rawValue -> Double.POSITIVE_INFINITY
            NEG_INFINITE.rawValue -> Double.NEGATIVE_INFINITY
            else -> convertDurationUnit(rawValue.toDouble(), LEAST_FILE_SIZE_UNIT, unit)
        }

    fun toLong(unit: FileSizeUnit): Long =
        when (rawValue) {
            INFINITE.rawValue -> Long.MAX_VALUE
            NEG_INFINITE.rawValue -> Long.MIN_VALUE
            else -> convertDurationUnit(rawValue, LEAST_FILE_SIZE_UNIT, unit)
        }

    val inBytes: Long
        get() = toLong(FileSizeUnit.BYTES)

    val inKilobytes: Long
        get() = toLong(FileSizeUnit.KILOBYTES)

    val inMegabytes: Long
        get() = toLong(FileSizeUnit.MEGABYTES)

    val inGigabytes: Long
        get() = toLong(FileSizeUnit.GIGABYTES)

    override fun toString(): String = when (rawValue) {
        0L -> "0b"
        INFINITE.rawValue -> "Infinity"
        NEG_INFINITE.rawValue -> "-Infinity"
        else -> {
            val isNegative = isNegative()
            buildString {
                if (isNegative) append('-')
                absoluteValue.toComponents { gigabytes, megabytes, kilobytes, bytes ->
                    val hasGigabytes = gigabytes != 0L
                    val hasMegabytes = megabytes != 0
                    val hasKilobytes = kilobytes != 0 || bytes != 0
                    var components = 0
                    if (hasGigabytes) {
                        append(gigabytes).append("gb")
                        components++
                    }
                    if (hasMegabytes || (hasGigabytes && hasKilobytes)) {
                        if (components++ > 0) append(' ')
                        append(megabytes).append("mb")
                    }
                    if (hasKilobytes) {
                        if (components++ > 0) append(' ')
                        when {
                            kilobytes != 0 || hasGigabytes || hasMegabytes ->
                                appendFractional(
                                    whole = kilobytes,
                                    fractional = bytes,
                                    fractionalSize = 3,
                                    unit = "kb"
                                )
                            bytes >= 1024 ->
                                appendFractional(
                                    whole = bytes / 1024,
                                    fractional = bytes % 1024,
                                    fractionalSize = 3,
                                    unit = "kb"
                                )
                            else ->
                                append(bytes).append('b')
                        }
                    }
                    if (isNegative && components > 1) insert(1, '(').append(')')
                }
            }
        }
    }

    private fun StringBuilder.appendFractional(
        whole: Int,
        fractional: Int,
        fractionalSize: Int,
        unit: String
    ) {
        append(whole)
        if (fractional != 0) {
            append('.')
            val fracString = fractional.toString().padStart(fractionalSize, '0')
            val nonZeroDigits = fracString.indexOfLast { it != '0' } + 1
            when {
                nonZeroDigits < 3 -> appendRange(fracString, 0, nonZeroDigits)
                else -> appendRange(fracString, 0, ((nonZeroDigits + 2) / 3) * 3)
            }
        }
        append(unit)
    }

}