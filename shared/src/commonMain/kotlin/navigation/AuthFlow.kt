package navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.auth.AuthComponent
import screens.auth.AuthComponent.AuthScreen.Create
import screens.auth.AuthComponent.AuthScreen.Forgot
import screens.auth.AuthComponent.AuthScreen.Login
import screens.auth.account_create.AccountCreateScreen
import screens.auth.account_forgot.AccountForgotScreen
import screens.auth.account_login.AccountLoginScreen

@Composable
internal fun AuthFlow(component: AuthComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(
            fade() + scale(
                animationSpec = tween(durationMillis = 400),
                frontFactor = 0.95F,
                backFactor = 1.15F
            )
        )
    ) {
        when (val instance = it.instance) {
            is Login -> AccountLoginScreen(instance.component)
            is Create -> AccountCreateScreen(instance.component)
            is Forgot -> AccountForgotScreen(instance.component)
        }
    }
}
