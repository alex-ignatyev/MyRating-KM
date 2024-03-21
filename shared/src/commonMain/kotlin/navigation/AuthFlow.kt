package navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.auth.AuthNavigation
import screens.auth.AuthNavigation.AuthScreen.Create
import screens.auth.AuthNavigation.AuthScreen.Forgot
import screens.auth.AuthNavigation.AuthScreen.Login
import screens.auth.account_create.AccountCreateScreen
import screens.auth.account_forgot.AccountForgotScreen
import screens.auth.account_login.AccountLoginScreen

@Composable
internal fun AuthFlow(component: AuthNavigation, rootModifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        animation = stackAnimation(
            fade() + scale(
                animationSpec = tween(durationMillis = 400),
                frontFactor = 0.95F,
                backFactor = 1.15F
            )
        )
    ) {
        when (val instance = it.instance) {
            is Login -> AccountLoginScreen(instance.component, rootModifier)
            is Create -> AccountCreateScreen(instance.component, rootModifier)
            is Forgot -> AccountForgotScreen(instance.component, rootModifier)
        }
    }
}
