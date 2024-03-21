package screens.auth.account_forgot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountForgotScreen(component: AccountForgotComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AccountForgotView(state, rootModifier) { action ->
        component.doAction(action)
    }
}
