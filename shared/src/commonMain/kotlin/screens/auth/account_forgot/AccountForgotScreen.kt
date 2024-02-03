package screens.auth.account_forgot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountForgotScreen(component: AccountForgotComponent) {

    val state by component.state.subscribeAsState()

    AccountForgotView(state) { action ->
        component.doAction(action)
    }
}
