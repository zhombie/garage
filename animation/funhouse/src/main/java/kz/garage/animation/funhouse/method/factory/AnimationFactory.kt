package kz.garage.animation.funhouse.method.factory

import kz.garage.animation.funhouse.method.Method
import kz.garage.animation.funhouse.method.Methods
import kz.garage.animation.funhouse.method.base.BaseSingleAnimation
import kz.garage.animation.funhouse.method.base.MultipleAnimations

internal interface AnimationFactory {
    fun create(method: Method): BaseSingleAnimation

    fun create(methods: Methods): MultipleAnimations?
}