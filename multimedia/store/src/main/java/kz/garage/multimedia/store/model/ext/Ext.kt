package kz.garage.multimedia.store.model.ext

import kz.garage.multimedia.store.model.Content
import kz.garage.multimedia.store.model.Media
import kz.garage.multimedia.store.model.Resolution

fun Content.getIdAsLongOrNull(): Long? =
    try {
        id.toLongOrNull()
    } catch (e: Exception) {
        null
    }

/**
 * [Media.Playable.duration]
 */

fun Content.getDuration(): Long? =
    if (this is Media.Playable) duration else null

fun Media.getDuration(): Long? =
    if (this is Media.Playable) duration else null

/**
 * [Resolution]
 */

fun Content.getResolution(): Resolution? =
    if (this is Media.Visual) resolution else null

fun Media.getResolution(): Resolution? =
    if (this is Media.Visual) resolution else null
