package kz.garage.json

import org.json.JSONArray

inline fun <reified T> JSONArray?.toMutableList(): MutableList<T> {
    if (this == null) return mutableListOf()

    val list = mutableListOf<T>()
    for (i in 0 until length()) {
        val item = this[i]
        if (item is T) {
            list.add(item)
        }
    }
    return list
}

inline fun <reified T> JSONArray.forEach(action: (T) -> Unit) {
    for (i in 0 until length()) {
        val item = get(i)
        if (item is T) {
            action(item)
        }
    }
}

inline fun <reified T, R> JSONArray.map(transform: (T) -> R): List<R> =
    mapTo(ArrayList(length()), transform)

inline fun <reified T, R, C: MutableCollection<in R>> JSONArray.mapTo(
    destination: C,
    transform: (T) -> R
): C {
    forEach<T> { item -> destination.add(transform(item)) }
    return destination
}

inline fun <reified T, R: Any> JSONArray.mapNotNull(transform: (T) -> R?): List<R> =
    mapNotNullTo(ArrayList(), transform)

inline fun <reified T, R: Any, C: MutableCollection<in R>> JSONArray.mapNotNullTo(
    destination: C,
    transform: (T) -> R?
): C {
    forEach<T> { element -> transform(element)?.let { destination.add(it) } }
    return destination
}