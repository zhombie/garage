package kz.garage.kotlin.time

import java.util.concurrent.TimeUnit

fun Long.toMillis(): Long =
    TimeUnit.SECONDS.toMillis(this)
