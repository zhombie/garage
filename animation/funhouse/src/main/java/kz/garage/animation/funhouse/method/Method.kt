package kz.garage.animation.funhouse.method

sealed class Method {

    sealed class Attention : Method() {
        object Bounce : Attention()
        object Flash : Attention()
        object Pulse : Attention()
        object RubberBand : Attention()
        object Shake : Attention()
        object StandUp : Attention()
        object Swing : Attention()
        object Tada : Attention()
        object Wave : Attention()
        object Wobble : Attention()
    }

    sealed class Bounce : Method() {
        object In : Bounce()
        object InLeft : Bounce()
        object InUp : Bounce()
        object InRight : Bounce()
        object InDown : Bounce()
    }

    sealed class Fade : Method() {
        object In : Fade()
        object InLeft : Fade()
        object InUp : Fade()
        object InRight : Fade()
        object InDown : Fade()

        object Out : Fade()
        object OutLeft : Fade()
        object OutUp : Fade()
        object OutRight : Fade()
        object OutDown : Fade()
    }

    sealed class Zoom : Method() {
        object In : Zoom()
        object InLeft : Zoom()
        object InUp : Zoom()
        object InRight : Zoom()
        object InDown : Zoom()

        object Out : Zoom()
        object OutLeft : Zoom()
        object OutUp : Zoom()
        object OutRight : Zoom()
        object OutDown : Zoom()
    }

}