package navigation

import DefaultRootComponent
import RootComponent.RootScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import screens.splash.SplashScreen
import ui.KalyanTheme

@Composable
internal fun RootFlow(component: DefaultRootComponent) {
    val rootModifier = Modifier.fillMaxSize().background(KalyanTheme.colors.background)
    Surface(modifier = rootModifier) {
        Children(
            stack = component.stack,
            modifier = rootModifier
        ) {
            when (val instance = it.instance) {
                is RootScreen.Splash -> SplashScreen(component = instance.component, rootModifier)
                is RootScreen.Login -> AuthFlow(component = instance.component, rootModifier)
                is RootScreen.Main -> MainScreensFlow(component = instance.component, rootModifier)
            }
        }
    }
}
