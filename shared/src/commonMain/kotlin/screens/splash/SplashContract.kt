package screens.splash

sealed interface SplashAction {
    data object InitScreen : SplashAction
}

class SplashState

sealed interface SplashEffect
