package kz.garage.animation.funhouse.method.factory

import kz.garage.animation.funhouse.method.base.BaseAnimation
import kz.garage.animation.funhouse.method.Method

internal interface AnimationFactory {
    fun create(method: Method): BaseAnimation?
}