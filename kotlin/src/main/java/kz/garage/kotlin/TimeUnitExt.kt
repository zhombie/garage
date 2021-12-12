package kz.garage.kotlin

import java.util.concurrent.TimeUnit

fun Long.toMillis(): Long =
    TimeUnit.SECONDS.toMillis(this)
