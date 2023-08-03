package screens.splash

import cafe.adriel.voyager.core.screen.Screen

sealed class SplashEvent {
    class InitSplashScreen : SplashEvent()
}

class SplashState

sealed class SplashAction {
    data class OpenFlow(val screen: Screen) : SplashAction()
}
