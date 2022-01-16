package kz.garage.file.size

import kz.garage.file.size.unit.FileSizeUnit

internal fun convertDurationUnit(
    value: Double,
    sourceUnit: FileSizeUnit,
    targetUnit: FileSizeUnit
): Double =
    when (sourceUnit) {
        FileSizeUnit.BYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value
                FileSizeUnit.KILOBYTES -> value / 1024.0
                FileSizeUnit.MEGABYTES -> value / 1024.0 / 1024.0
                FileSizeUnit.GIGABYTES -> value / 1024.0 / 1024.0 / 1024.0
            }
        }
        FileSizeUnit.KILOBYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024.0
                FileSizeUnit.KILOBYTES -> value
                FileSizeUnit.MEGABYTES -> value / 1024.0
                FileSizeUnit.GIGABYTES -> value / 1024.0 / 1024.0
            }
        }
        FileSizeUnit.MEGABYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024.0 * 1024.0
                FileSizeUnit.KILOBYTES -> value * 1024.0
                FileSizeUnit.MEGABYTES -> value
                FileSizeUnit.GIGABYTES -> value / 1024.0
            }
        }
        FileSizeUnit.GIGABYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024.0 * 1024.0 * 1024.0
                FileSizeUnit.KILOBYTES -> value * 1024.0 * 1024.0
                FileSizeUnit.MEGABYTES -> value * 1024.0
                FileSizeUnit.GIGABYTES -> value
            }
        }
    }


internal fun convertDurationUnit(
    value: Long,
    sourceUnit: FileSizeUnit,
    targetUnit: FileSizeUnit
): Long =
    when (sourceUnit) {
        FileSizeUnit.BYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value
                FileSizeUnit.KILOBYTES -> value / 1024
                FileSizeUnit.MEGABYTES -> value / 1024 / 1024
                FileSizeUnit.GIGABYTES -> value / 1024 / 1024 / 1024
            }
        }
        FileSizeUnit.KILOBYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024
                FileSizeUnit.KILOBYTES -> value
                FileSizeUnit.MEGABYTES -> value / 1024
                FileSizeUnit.GIGABYTES -> value / 1024 / 1024
            }
        }
        FileSizeUnit.MEGABYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024 * 1024
                FileSizeUnit.KILOBYTES -> value * 1024
                FileSizeUnit.MEGABYTES -> value
                FileSizeUnit.GIGABYTES -> value / 1024
            }
        }
        FileSizeUnit.GIGABYTES -> {
            when (targetUnit) {
                FileSizeUnit.BYTES -> value * 1024 * 1024 * 1024
                FileSizeUnit.KILOBYTES -> value * 1024 * 1024
                FileSizeUnit.MEGABYTES -> value * 1024
                FileSizeUnit.GIGABYTES -> value
            }
        }
    }
