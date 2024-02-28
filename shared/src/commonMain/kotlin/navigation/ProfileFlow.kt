package navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.main.profile.ProfileComponent
import screens.main.profile.ProfileComponent.ProfileScreen.ChangePassword
import screens.main.profile.ProfileComponent.ProfileScreen.User
import screens.main.profile.change_password.ChangePasswordScreen
import screens.main.profile.user.UserScreen

@Composable
internal fun ProfileFlow(component: ProfileComponent, rootModifier: Modifier = Modifier) {
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
            is User -> UserScreen(instance.component, rootModifier)
            is ChangePassword -> ChangePasswordScreen(instance.component, rootModifier)
        }
    }
}
