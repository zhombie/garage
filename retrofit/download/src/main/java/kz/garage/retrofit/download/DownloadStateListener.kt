package kz.garage.retrofit.download

fun interface DownloadStateListener {

    sealed class State private constructor(
        open val contentLength: Long
    ) {

        val isContentLengthUnknown: Boolean
            get() = contentLength <= 0

        data class Loading constructor(
            val bytesRead: Long,
            override val contentLength: Long
        ) : State(contentLength) {

            val percentage: Float
                get() = if (isContentLengthUnknown) 0F else bytesRead * 100F / contentLength

        }

        data class Completed constructor(
            override val contentLength: Long
        ) : State(contentLength)

    }

    fun onState(state: State)
}