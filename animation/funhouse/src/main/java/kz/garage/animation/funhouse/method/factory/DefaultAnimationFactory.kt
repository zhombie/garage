package kz.garage.animation.funhouse.method.factory

import kz.garage.animation.funhouse.method.Method
import kz.garage.animation.funhouse.method.base.BaseAnimation
import kz.garage.animation.funhouse.method.internal.attention.*
import kz.garage.animation.funhouse.method.internal.bounce.enter.*
import kz.garage.animation.funhouse.method.internal.fade.enter.*
import kz.garage.animation.funhouse.method.internal.fade.exit.*
import kz.garage.animation.funhouse.method.internal.zoom.enter.*
import kz.garage.animation.funhouse.method.internal.zoom.exit.*

internal class DefaultAnimationFactory : AnimationFactory {

    override fun create(method: Method): BaseAnimation? {
        return when (method) {
            // Attention
            Method.Attention.Bounce -> Bounce()
            Method.Attention.Flash -> Flash()
            Method.Attention.Pulse -> Pulse()
            Method.Attention.RubberBand -> RubberBand()
            Method.Attention.Shake -> Shake()
            Method.Attention.StandUp -> StandUp()
            Method.Attention.Swing -> Swing()
            Method.Attention.Tada -> Tada()
            Method.Attention.Wave -> Wave()
            Method.Attention.Wobble -> Wobble()

            // Bounce Enter
            Method.Bounce.In -> BounceIn()
            Method.Bounce.InLeft -> BounceInLeft()
            Method.Bounce.InUp -> BounceInUp()
            Method.Bounce.InRight -> BounceInRight()
            Method.Bounce.InDown -> BounceInDown()

            // Fade Enter
            Method.Fade.In -> FadeIn()
            Method.Fade.InLeft -> FadeInLeft()
            Method.Fade.InUp -> FadeInUp()
            Method.Fade.InRight -> FadeInRight()
            Method.Fade.InDown -> FadeInDown()

            // Fade Exit
            Method.Fade.Out -> FadeOut()
            Method.Fade.OutLeft -> FadeOutLeft()
            Method.Fade.OutUp -> FadeOutUp()
            Method.Fade.OutRight -> FadeOutRight()
            Method.Fade.OutDown -> FadeOutDown()

            // Zoom Enter
            Method.Zoom.In -> ZoomIn()
            Method.Zoom.InLeft -> ZoomInLeft()
            Method.Zoom.InUp -> ZoomInUp()
            Method.Zoom.InRight -> ZoomInRight()
            Method.Zoom.InDown -> ZoomInDown()

            // Zoom Exit
            Method.Zoom.Out -> ZoomOut()
            Method.Zoom.OutLeft -> ZoomOutLeft()
            Method.Zoom.OutUp -> ZoomOutUp()
            Method.Zoom.OutRight -> ZoomOutRight()
            Method.Zoom.OutDown -> ZoomOutDown()

            else -> null
        }
    }

}