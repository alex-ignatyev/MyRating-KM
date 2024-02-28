package navigation

import DefaultRootComponent
import RootComponent.RootScreen
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import screens.splash.SplashScreen

@Composable
internal fun RootFlow(component: DefaultRootComponent) {
    Surface {
        Children(
            stack = component.stack
        ) {
            when (val instance = it.instance) {
                is RootScreen.Splash -> SplashScreen(component = instance.component, Modifier)
                is RootScreen.Login -> AuthFlow(component = instance.component, Modifier)
                is RootScreen.Main -> MainScreensFlow(component = instance.component, Modifier)
            }
        }
    }
}
