package screens.auth.account_login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountLoginScreen(component: AccountLoginComponent) {

    val state by component.state.subscribeAsState()

    AccountLoginView(state) { action ->
        component.doAction(action)
    }
}
