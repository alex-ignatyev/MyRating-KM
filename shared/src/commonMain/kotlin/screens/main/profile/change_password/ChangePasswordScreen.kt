package screens.main.profile.change_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ChangePasswordScreen(component: ChangePasswordComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    ChangePasswordView(state, rootModifier) { action ->
        component.doAction(action)
    }
}
