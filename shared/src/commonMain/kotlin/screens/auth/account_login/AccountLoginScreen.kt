package screens.auth.account_login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountLoginScreen(component: AccountLoginComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AccountLoginView(state, rootModifier) { action ->
        component.doAction(action)
    }
}
