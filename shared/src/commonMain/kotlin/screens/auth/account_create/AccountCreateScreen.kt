package screens.auth.account_create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountCreateScreen(component: AccountCreateComponent) {

    val state by component.state.subscribeAsState()

    AccountCreateView(state) { action ->
        component.doAction(action)
    }
}
