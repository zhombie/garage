package kz.garage.file.extension

import java.io.File

fun File.findExtension(): Extension? =
    Extension::class.java.enumConstants?.find { it.value == extension.lowercase() }
