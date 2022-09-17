package kz.garage.image.load

import android.content.Context
import androidx.annotation.Px

sealed interface Transformation {

    data class RoundedCorners constructor(
        @Px val topLeft: Float = 0f,
        @Px val topRight: Float = 0f,
        @Px val bottomLeft: Float = 0f,
        @Px val bottomRight: Float = 0f
    ) : Transformation

    data class BlurTransformation @JvmOverloads constructor(
        val context: Context,
        val radius: Float = 10f,
        val sampling: Float = 1f
    ) : Transformation

    object CircleCrop : Transformation

    object Grayscale : Transformation

}