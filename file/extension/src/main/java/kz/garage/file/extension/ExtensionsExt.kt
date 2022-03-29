package kz.garage.file.extension

operator fun Extensions.contains(element: Extension): Boolean =
    values.contains(element)
